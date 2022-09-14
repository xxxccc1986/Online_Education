<template>
    <div>
        <!-- 阿里云视频播放器样式 -->
        <link rel="stylesheet" href="https://g.alicdn.com/de/prismplayer/2.8.1/skins/default/aliplayer-min.css" >
        <!-- 阿里云视频播放器脚本 -->
        <script charset="utf-8" type="text/javascript" src="https://g.alicdn.com/de/prismplayer/2.8.1/aliplayer-min.js" />

        <!-- 定义播放器dom -->
        <div id="J_prismPlayer" class="prism-player" />
    </div>
</template>

<script>
import vod from '@/api/vod'

export default {
layout: 'video',
name:'videoId',
data() {
    return {
        videoId: '',
        videoAuth:'',
    }
},
created() {
    this.videoId = this.$route.params.id
    this.getVideoAuth()
},
methods: {
    getVideoAuth(){
        vod.getVideoAuth(this.videoId)
         .then(response => {
            //填充返回的视频凭证
            this.videoAuth = response.data.data
            //创建播放器
            this.createVideoPlay()
         })    
    },
    //创建播放器
    createVideoPlay(){
        new Aliplayer({
            id: 'J_prismPlayer',
            vid: this.videoId, // 视频id
            playauth: this.videoAuth, // 播放凭证
            encryptType: '1', // 如果播放加密视频，则需设置encryptType=1，非加密视频无需设置此项
            width: '100%',
            height: '500px',
            autoplay: false,
            }, function(player) {
            console.log('播放器创建成功')
    })
    }
},
}
</script>