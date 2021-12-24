#!/bin/bash

sudo apt-get install playerctl

if [[ -n $(type -p java) ]] || [[ -n "$JAVA_HOME" ]]; then
    echo "Java already installed"
else
    sudo apt install openjdk-11-jdk
    echo "export JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64" >> $HOME/.bashrc
    echo "export PATH=\$PATH:\$JAVA_HOME/bin" >> $HOME/.bashrc
fi

echo ""
./mvnw package -DskipTests=true

LINK="/usr/bin/remotecontrol"

DIR=$(pwd)

if [ ! -e "$DIR/bin" ]; then
    mkdir bin
fi

echo "#!/usr/bin/env sh" > bin/remotecontrol
echo "java -jar $DIR/target/RemoteControl.jar" >> bin/remotecontrol

sudo chmod +x $DIR/bin/remotecontrol

if [[ ! -L $LINK ]]; then
    sudo ln -s $DIR/bin/remotecontrol /usr/bin/remotecontrol
fi

echo ""
echo "############################################################"
echo "#   To Start the application run \"remotecontrol\" command   #"
echo "############################################################"