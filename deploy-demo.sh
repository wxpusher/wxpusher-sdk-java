#!/usr/bin/env bash
if [[ $1 == "-p" ]]; then
  deployPath=/data/app/wxpusher-prod/wxpusher-demo/
  server=101.37.14.29
  appService=app.wxpusher.prod.demo
  echo "部署生成环境"
else
  deployPath=/data/app/wxpusher-test/wxpusher-demo/
  server=115.28.141.240
  appService=app.wxpusher.test.demo
  echo "部署测试环境"
fi

./gradlew clean assemble


jarPath=`find demo/build/libs/demo-*.jar`
echo "输出文件：${jarPath}"
jarName=${jarPath##*/}


ssh root@${server} "
if [ ! -d ${deployPath} ]; then
    echo  "文件夹不存在,创建文件夹"
    mkdir ${deployPath}
fi
cd ${deployPath}
if [ -f ${jarName} ]; then
  rm ${jarName}
  echo 删除文件...
fi
"

echo "上传文件:${jarPath}"
scp ${jarPath} root@${server}:${deployPath}${jarName}
echo "上传完成"

echo "重启服务"
ssh root@${server} "systemctl restart ${appService}"
echo "部署完成"
