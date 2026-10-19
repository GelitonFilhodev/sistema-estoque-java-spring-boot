terraform {
  required_providers {
    docker = {
      source  = "kreuzwerker/docker"
      version = "~> 3.0"
    }
  }
}

provider "docker" {}

variable "db_password" {
  type      = string
  sensitive = true
}

# Rede, volume e banco
resource "docker_network" "estoque" {
  name = "estoque-net"
}

resource "docker_volume" "db_data" {
  name = "estoque-db-data"
}

resource "docker_image" "mysql" {
  name = "mysql:8.4"
}

resource "docker_container" "db" {
  name  = "estoque-db-tf"
  image = docker_image.mysql.image_id

  networks_advanced {
    name = docker_network.estoque.name
  }

  env = [
    "MYSQL_ROOT_PASSWORD=${var.db_password}",
    "MYSQL_DATABASE=estoque",
  ]

  ports {
    internal = 3306
    external = 3307
  }

  volumes {
    volume_name    = docker_volume.db_data.name
    container_path = "/var/lib/mysql"
  }
}

# API (builda a imagem usando o Dockerfile da raiz do projeto)
resource "docker_image" "api" {
  name = "estoque-api-tf"

  build {
    context = ".."
  }
}

resource "docker_container" "api" {
  name    = "estoque-api-tf"
  image   = docker_image.api.image_id
  restart = "on-failure"

  networks_advanced {
    name = docker_network.estoque.name
  }

  env = [
    "SPRING_DATASOURCE_URL=jdbc:mysql://estoque-db-tf:3306/estoque?allowPublicKeyRetrieval=true&useSSL=false",
    "DB_USER=root",
    "DB_PASSWORD=${var.db_password}",
  ]

  ports {
    internal = 8080
    external = 8080
  }

  depends_on = [docker_container.db]
}