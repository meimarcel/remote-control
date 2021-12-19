#!/bin/sh
sudo apt-get install playerctl
sudo apt install openjdk-11-jdk

EXIST_JAVA_HOME=$(cat $HOME/.bashrc | grep JAVA_HOME)

if [ -z "$EXIST_JAVA_HOME" ]; then
    echo "\nexport JAVA_HOME=/usr/lib/jvm/java-11-openjdk-amd64\nexport PATH=\$PATH:\$JAVA_HOME/bin" >> $HOME/.bashrc
else
    echo "Variable JAVA_HOME already defined"
fi

./mvnw package -DskipTests=true

LINK="/usr/bin/remotecontrol"

DIR=$(pwd)

if [ ! -e "$DIR/bin" ]; then
    mkdir bin
fi

echo "#!/usr/bin/env sh" > bin/remotecontrol
echo "\njava -jar $DIR/target/RemoteControl.jar" >> bin/remotecontrol

sudo chmod +x $DIR/bin/remotecontrol

if [ ! -L $LINK ]; then
    sudo ln -s $DIR/bin/remotecontrol /usr/bin/remotecontrol
fi

echo "\n############################################################"
echo "#   To Start the application run \"remotecontrol\" command   #"
echo "############################################################\n"