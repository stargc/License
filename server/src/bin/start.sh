#!/usr/bin/env bash
base_dir=$(dirname $0)/..
cd $base_dir
# loading dependency jar in lib directory
CLASSPATH=config:$CLASSPATH
for file in lib/*.jar;
do
  CLASSPATH=$CLASSPATH:$file
done
class_path="$CLASSPATH":$base_dir/config
java_opt="-server -Xms2048m -Xmx2048m -Duser.country=CN -Duser.language=zh"
main_class=com.ehl.ServerApplication
bin/daemon.sh start "$class_path" "$main_class" "$java_opt"