<template>
  <div id="aCoursesList" class="bg-fa of">
    <!-- /课程详情 开始 -->
    <section class="container">
      <section class="path-wrap txtOf hLh30">
        <a href="/" title class="c-999 fsize14">首页</a>
        \
        <a href="/course" title class="c-999 fsize14">课程列表</a>
        \
        <span class="c-333 fsize14">{{courseDetails.title}}</span>
      </section>
      <div>
        <article class="c-v-pic-wrap" style="height: 358px;">
          <section class="p-h-video-box" id="videoPlay">
            <img :src="courseDetails.cover" style="height: 357px;padding-left: 6px" :alt="courseDetails.title" class="dis c-v-pic">
          </section>
        </article>
        <aside class="c-attr-wrap">
          <section class="ml20 mr15">
            <h2 class="hLh30 txtOf mt15">
              <span class="c-fff fsize24">{{courseDetails.title}}</span>
            </h2>
            <section class="c-attr-jg">
              <span class="c-fff">价格：</span>
              <b class="c-yellow" style="font-size:24px;">￥{{courseDetails.price}}</b>
            </section>
            <section class="c-attr-mt c-attr-undis">
              <span class="c-fff fsize14">主讲： {{courseDetails.name}}&nbsp;&nbsp;&nbsp;</span>
            </section>
            <section class="c-attr-mt of">
              <span class="ml10 vam">
                <em class="icon18 scIcon"></em>
                <a class="c-fff vam" title="收藏" href="#" >收藏</a>
              </span>
            </section>
            <section v-if="isFree" class="c-attr-mt">
              <a href="#" title="立即观看" class="comm-btn c-btn-3">立即观看</a>
            </section>
            <section v-if="isBuy" class="c-attr-mt">
              <a href="#" @click.prevent="createOrder" title="立即购买" class="comm-btn c-btn-3">立即购买</a>
            </section>
          </section>
        </aside>
        <aside class="thr-attr-box">
          <ol class="thr-attr-ol clearfix">
            <li>
              <p>&nbsp;</p>
              <aside>
                <span class="c-fff f-fM">购买数</span>
                <br>
                <h6 class="c-fff f-fM mt10">{{courseDetails.buyCount}}</h6>
              </aside>
            </li>
            <li>
              <p>&nbsp;</p>
              <aside>
                <span class="c-fff f-fM">课时数</span>
                <br>
                <h6 class="c-fff f-fM mt10">{{courseDetails.lessonNum}}</h6>
              </aside>
            </li>
            <li>
              <p>&nbsp;</p>
              <aside>
                <span class="c-fff f-fM">浏览数</span>
                <br>
                <h6 class="c-fff f-fM mt10">{{courseDetails.viewCount}}</h6>
              </aside>
            </li>
          </ol>
        </aside>
        <div class="clear"></div>
      </div>
      <!-- /课程封面介绍 -->
      <div class="mt20 c-infor-box">
        <article class="fl col-7">
          <section class="mr30">
            <div class="i-box">
              <div>
                <section id="c-i-tabTitle" class="c-infor-tabTitle c-tab-title">
                  <a name="c-i" class="current" title="课程详情">课程详情</a>
                </section>
              </div>

              <article class="ml10 mr10 pt20">
                <div>
                  <h6 class="c-i-content c-infor-title">
                    <span>课程介绍</span>
                  </h6>
                  <div class="course-txt-body-wrap">
                    <section class="course-txt-body">
                      <p v-html="courseDetails.description">
                        <!-- {{this.courseDetails.description}} -->
                      </p>
                    </section>
                  </div>
                </div>
                <!-- /课程介绍 -->
                <div class="mt50">
                  <h6 class="c-g-content c-infor-title">
                    <span>课程大纲</span>
                  </h6>
                  <section class="mt20">
                    <div class="lh-menu-wrap">
                      <menu id="lh-menu" class="lh-menu mt10 mr10">
                        <ul>
                          <!-- 文件目录 -->
                          <li class="lh-menu-stair" v-for="chapter in this.courseChapterList" :key="chapter.id">
                            <a href="javascript: void(0)" :title="chapter.title" class="current-1">
                              <em class="lh-menu-i-1 icon18 mr10"></em>{{chapter.title}}
                            </a>
                            <ol class="lh-menu-ol" style="display: block;">
                              <li class="lh-menu-second ml30" v-for="video in chapter.courseBarList" :key="video.id">
                                <!-- target="_blank"表示跳转到一个新页面打开 -->
                                <a :href="'/video/' + video.videoSourceId" title target="_blank">
                                  <span class="fr">
                                    <i class="free-icon vam mr10">免费试听</i>
                                  </span>
                                  <em class="lh-menu-i-2 icon16 mr5">&nbsp;</em>{{video.title}}
                                </a>
                              </li>
                            </ol>
                          </li>
                        </ul>
                      </menu>
                    </div>
                  </section>
                </div>
                <!-- /课程大纲 -->
                <div class="mt50 commentHtml"><div>
                  <h6 class="c-c-content c-infor-title" id="i-art-comment">
                    <span class="commentTitle">课程评论</span>
                  </h6>
                  <section class="lh-bj-list pr mt20 replyhtml">  
                    <ul>
                      <li class="unBr">
                        <aside class="noter-pic">
                          <img width="50" height="50" class="picImg" src="~/assets/img/avatar-boy.gif">
                        </aside>

                        <div class="of">
                          <section class="n-reply-wrap">
                            <fieldset>
                              <textarea  v-model="comment.content" placeholder="输入您要评论的文字" id="commentContent"/>
                            </fieldset>
                            <div class="el-form-item" style="padding-left:480px">
                              <label class="el-form-item__label">课程评分：</label>
                              <label class="el-form-item__label" style="padding-top:12px">
                                <el-rate
                                  show-score
                                  v-model="comment.rate"
                                  :colors="colors">
                                </el-rate>
                              </label>
                            </div>
                            <p class="of mt5 tar pl10 pr10">
                              <span class="fl">
                                <tt class="c-red commentContentmeg" style="display: none;"></tt>
                              </span>
                              <input type="button" @click="createComment" value="回复" class="lh-reply-btn">
                            </p>
                          </section>
                        </div>
                      </li>
                    </ul>
                  </section>
                  <section class="">
                      <section class="question-list lh-bj-list pr">
                        <ul class="pr10">
                          <li v-if="comments.length === 0">
                            <div style="align-items: center;padding-left:30%">
                              暂无评论，快来发第一条评论吧
                            </div>
                          </li>
                          <li v-for="(comment,index) in comments" v-bind:key="index">
                              <aside class="noter-pic">
                                <img width="50" height="50" class="picImg" :src="comment.avatar">
                                  <span style="padding-left:8px;padding-top:20px" class="fsize12 c-blue">
                                    {{comment.nickname}}
                                  </span>
                                </aside>
                              <div class="of">
                                <span class="fl"> 
                                  <div class="fsize12 c-999 ml5 ">
                                    <label style="padding-top:10px">
                                      <el-rate
                                        v-model="comment.rate"
                                        :disabled='true'
                                        show-score
                                        :colors="colors">
                                      </el-rate>
                                    </label>
                                  </div> 
                                </span>
                              </div>
                              <div class="noter-txt mt5" style="padding-top:5px;padding-left:5px">
                                <p>
                                  {{comment.content}}
                                </p>
                              </div>                          
                              <div class="of mt5">
                                <span class="fr">
                                  <font class="fsize12 c-999 ml5">
                                    {{comment.gmtCreate}}
                                  </font>
                                  <font class="fsize12 c-999 ml5">
                                    <el-button type="text" size="mini" @click="reportComment(comment.id)">
                                        举报
                                    </el-button>                                   
                                  </font>                             
                                </span>                               
                              </div>
                            </li>
                          </ul>
                      </section>
                    </section> 
                    <!-- 公共分页 开始 -->
                      <div v-if="this.comments.length !== 0" class="paging">
                          <!-- undisable这个class是否存在，取决于数据属性hasPrevious -->
                          <a
                          :class="{undisable: !data.hasPrevious}"
                          href="#"
                          title="首页"
                          @click.prevent="pageQuery(1)">首</a>
                          <a
                          :class="{undisable: !data.hasPrevious}"
                          href="#"
                          title="前一页"
                          @click.prevent="pageQuery(data.current-1)">&lt;</a>
                          <a
                          v-for="page in data.pages"
                          :key="page"
                          :class="{current: data.current == page, undisable: data.current == page}"
                          :title="'第'+page+'页'"
                          href="#"
                          @click.prevent="pageQuery(page)">{{ page }}</a>
                          <a
                          :class="{undisable: !data.hasNext}"
                          href="#"
                          title="后一页"
                          @click.prevent="pageQuery(data.current + 1)">&gt;</a>
                          <a
                          :class="{undisable: !data.hasNext}"
                          href="#"
                          title="末页"
                          @click.prevent="pageQuery(data.pages)">末</a>
                          <div class="clear"/>
                      </div>
                      <!-- 公共分页 结束 -->
                  </div>
                </div>
                <!-- /课程评论结束 -->
              </article>
            </div>
          </section>     
        </article>     
        <aside class="fl col-3">
          <div class="i-box">
            <div>
              <section class="c-infor-tabTitle c-tab-title">
                <a title href="javascript:void(0)">主讲讲师</a>
              </section>
              <section class="stud-act-list">
                <ul style="height: auto;">
                  <li>
                    <div class="u-face">
                      <a href="#">
                        <img :src="this.courseDetails.avatar" width="50" height="50" :alt="this.courseDetails.name">
                      </a>
                    </div>
                    <section class="hLh30 txtOf">
                      <a class="c-333 fsize16 fl" href="#">{{courseDetails.name}}</a>
                    </section>
                    <section class="hLh20 txtOf">
                      <span class="c-999">{{courseDetails.career}}</span>
                    </section>
                  </li>
                </ul>
              </section>
            </div>
          </div>
        </aside>
        <div class="clear"></div>
      </div>
    </section>
    <!-- /课程详情 结束 -->
  </div>
</template>

<script>
import course from '@/api/course'
import order from '@/api/order'
import cookie from 'js-cookie'
import comment from '@/api/comment'

export default {
  name:'courseDetails',
  data() {
    return {
      courseId: '',
      courseDetails:{},
      courseChapterList:[],
      data:{},
      pageNum: 1,
      pageSize: 3,
      isFree: true, //表示免费
      isBuy: false, //表示收费
      comment:{ rate:5},
      comments:[],
      colors: ['#99A9BF', '#F7BA2A', '#FF9900']
    }
  },
  created() {
    //获取url上的课程id
    this.courseId = this.$route.params.id
    this.getCourseDetailsById()
  },
  methods: {
    //获取课程信息
    getCourseDetailsById(){
      course.getCourseDetailsById(this.courseId)
        .then(response => {
            //将查询的数据填充至列表
            this.courseDetails = response.data.data.courseDetails
            this.courseChapterList = response.data.data.courseChapterList

            //调用判断课程显示收费还是免费
            this.judgeCourseIfNeedBuy()

            //显示当前课程相关的评论
            this.showCommnet()
        })
    },
    //购买课程
    createOrder(){
      //先判断是否能从cookie中取到信息
      let user = cookie.get('userInfo')
      if(!user){ //未登录不允许进行购买
        this.$message.error("请先登录再尝试购买！")
        return false;
      }
      order.createOrder(this.courseId)
        .then(response=> {
           console.log("订单号是：" + response.data.data);
           //获取返回的订单号
            let orderNo = response.data.data
            //跳转到支付页面
            this.$router.push({path:'/order/'+ orderNo})
        })
    },
    goToPay(){
      console.log("跳转了");
      order.createOrder(this.courseId)
          .then(response => {
            console.log("订单号是：" + response.data.data);
            //获取返回的订单号
            let orderNo = response.data.data
            //跳转到支付页面
            this.$router.push({path:'/order/'+ orderNo})
          })
    },
    //判断课程是否需要收费
    judgeCourseIfNeedBuy(){
      //先判断是否能从cookie中取到信息
      let user = cookie.get('userInfo')
      if(user){ //非空或者非0数字代表已经登录
          //查询是否已经购买此课程
          this.judgeCourseIsBuyOrNot()
      }else{ //代表未登录
        //仅需判断课程价格是否为0即可
        if(this.courseDetails.price !== 0){
            //设置为付费
            this.isFree = false 
            this.isBuy = true
          }
      }   
    },
    //查询用户是否已购买当前课程
    judgeCourseIsBuyOrNot(){
      course.judgeCourseIsBuyOrNot(this.courseId)
        .then(response => {
          if(!response.data.data){ //表示未购买
              this.isFree = false
              this.isBuy = true
          }
        })
    },
    //创建评论
    createComment(){
      //先判断是否能从cookie中取到信息
      let user = cookie.get('userInfo')
      if(user){ //非空或者非0数字代表已经登录
        // debugger
        // console.log("发送请求前------->" + this.comment.content);
        comment.createComment(this.comment.content,this.courseId,this.comment.rate)
          .then(response => {
            // debugger
            // console.log("发送请求后------->" +this.comment.content);
            this.$message.success("回复成功！")
            //刷新评论信息
            this.showCommnet()
          })
      }else {
        this.$message.error("请先登录再进行评论！")
      }
    },
    //查看课程评论
    showCommnet(){
      comment.showCommnet(this.pageNum,this.pageSize,this.courseId,this.courseDetails.teacherId)
            .then(response => {
              this.data = response.data.data
              //填充评论数据
              this.comments = response.data.data.lists
            })
    },
    //举报评论
    reportComment(commentId){
      this.$confirm('确定要举报该评论?', '举报提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
      comment.reportComment(commentId)
        .then(response => {
          this.$message.success("举报成功！")
        })
      })
    },
    //分页查询
    pageQuery(pageNum){
      if(pageNum > this.pages || pageNum < 1 || pageNum === this.pageNum ){
        return false;
      }
      //将输入的页数置为当前页
      this.pageNum = pageNum
      //调用方法进行查询
      this.showCommnet()
    }
  },
};
</script>