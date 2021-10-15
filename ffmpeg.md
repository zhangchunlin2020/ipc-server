# 查看视频文件编码格式
~~~
/opt/ffmpeg/ffprobe file.mp4 -show_streams -select_streams v -print_format json 
~~~

~~~
ffmpeg -i rtsp://wowzaec2demo.streamlock.net/vod/mp4:BigBuckBunny_115k.mov -vcodec libx264 -acodec copy -f flv rtmp://192.168.56.101:1935/mylive/1
~~~