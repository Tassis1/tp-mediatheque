Vagrant.configure("2") do |config|

  # ============================================================
  # GENERAL
  # ============================================================

  config.vm.box = "ubuntu/jammy64"
  config.vm.hostname = "serveur-minikube"

  # Désactivation du shared folder automatique /vagrant.
  # Important : le projet est situé dans WSL et Vagrant tourne
  # sous Windows.
  config.vm.synced_folder ".", "/vagrant", disabled: true


  # ============================================================
  # NETWORK
  # ============================================================

  # Réseau privé entre la machine Windows et la VM
  config.vm.network "private_network",
                    ip: "192.168.56.10"

  # SSH Vagrant
  config.vm.network "forwarded_port",
                    guest: 22,
                    host: 2222,
                    auto_correct: true

  # Application frontend
  config.vm.network "forwarded_port",
                    guest: 3000,
                    host: 3000,
                    auto_correct: true

  # Backend
  config.vm.network "forwarded_port",
                    guest: 8080,
                    host: 8080,
                    auto_correct: true

  # Kubernetes API Server
  config.vm.network "forwarded_port",
                    guest: 6443,
                    host: 6443,
                    auto_correct: true

  # NodePort HTTP
  config.vm.network "forwarded_port",
                    guest: 30080,
                    host: 30080,
                    auto_correct: true

  # NodePort HTTPS
  config.vm.network "forwarded_port",
                    guest: 30443,
                    host: 30443,
                    auto_correct: true


  # ============================================================
  # VIRTUALBOX
  # ============================================================

  config.vm.provider "virtualbox" do |vb|

    # Nom de la VM dans VirtualBox
    vb.name = "tp-mediatheque"

    # Ressources
    vb.memory = 8192
    vb.cpus = 4

    # Désactiver l'interface graphique VirtualBox
    vb.gui = false

    # Nom de la carte réseau
    vb.customize [
      "modifyvm",
      :id,
      "--nictype1",
      "82540EM"
    ]

  end


  # ============================================================
  # PROVISIONING
  # ============================================================

  config.vm.provision "shell",
    inline: <<-SHELL

      echo "========================================="
      echo " Provisionnement tp-mediatheque"
      echo "========================================="

      export DEBIAN_FRONTEND=noninteractive

      echo "[1/8] Mise à jour du système..."

      apt-get update -y
      apt-get upgrade -y


      echo "[2/8] Installation des outils de base..."

      apt-get install -y \
        curl \
        wget \
        git \
        unzip \
        zip \
        vim \
        nano \
        htop \
        tree \
        jq \
        net-tools \
        iputils-ping \
        dnsutils \
        ca-certificates \
        gnupg \
        lsb-release \
        software-properties-common \
        apt-transport-https


      echo "[3/8] Installation de Python / Ansible..."

      apt-get install -y \
        python3 \
        python3-pip \
        python3-venv \
        ansible


      echo "[4/8] Installation de Docker..."

      install -m 0755 -d /etc/apt/keyrings

      curl -fsSL https://download.docker.com/linux/ubuntu/gpg \
        -o /etc/apt/keyrings/docker.asc

      chmod a+r /etc/apt/keyrings/docker.asc

      echo \
        "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.asc] https://download.docker.com/linux/ubuntu \
        $(. /etc/os-release && echo "$VERSION_CODENAME") stable" \
        > /etc/apt/sources.list.d/docker.list

      apt-get update -y

      apt-get install -y \
        docker-ce \
        docker-ce-cli \
        containerd.io \
        docker-buildx-plugin \
        docker-compose-plugin


      echo "[5/8] Configuration Docker..."

      systemctl enable docker
      systemctl start docker

      usermod -aG docker vagrant


      echo "[6/8] Installation de kubectl..."

      curl -LO "https://dl.k8s.io/release/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/linux/amd64/kubectl"

      install -o root -g root -m 0755 kubectl /usr/local/bin/kubectl

      rm -f kubectl


      echo "[7/8] Installation de Minikube..."

      curl -LO https://storage.googleapis.com/minikube/releases/latest/minikube-linux-amd64

      install minikube-linux-amd64 /usr/local/bin/minikube

      rm -f minikube-linux-amd64


      echo "[8/8] Installation de Helm..."

      curl https://raw.githubusercontent.com/helm/helm/main/scripts/get-helm-3 | bash


      echo ""
      echo "========================================="
      echo " Installation terminée"
      echo "========================================="

      echo ""
      echo "Versions :"

      docker --version || true
      kubectl version --client || true
      minikube version || true
      helm version || true
      ansible --version | head -n 1 || true

      echo ""
      echo "IP de la VM :"
      ip addr show

      echo ""
      echo "========================================="

    SHELL

end
