<template>
  <div id="aCoursesList" class="bg-fa of">
    <!-- /课程列表 开始 -->
    <section class="container">
      <header class="comm-title">
        <h2 class="fl tac">
          <span class="c-333">全部课程</span>
        </h2>
      </header>
      <section class="c-sort-box">
        <section class="c-s-dl">
          <dl>
            <dt>
              <span class="c-999 fsize14">课程类别</span>
            </dt>
            <dd class="c-s-dl-li">
              <ul class="clearfix">
                <li  :class="{current:oneIndex === -1}">
                  <a title="全部" href="#" @click.prevent="clear">全部</a>
                </li>
                <li v-for="(oneSubject,index) in this.oneSubjectList" :key="index" :class="{current:oneIndex == index}">
                  <a :title="oneSubject.title" href="#" @click.prevent="oneSubjectClickQuery(oneSubject.id,index)">{{oneSubject.title}}</a>
                </li>
              </ul>
            </dd>
          </dl>
          <dl>
            <dt>
              <span class="c-999 fsize14"></span>
            </dt>
            <dd class="c-s-dl-li">
              <ul v-if="twoSubjectList.length" class="clearfix">
                <li v-for="(twoSubject,index) in twoSubjectList" :key="twoSubject.id" :class="{current:twoIndex == index}">
                  <a :title="twoSubject.title" href="#" @click.prevent="twoSubjectClickQuery(twoSubject.id,index)">{{twoSubject.title}}</a>
                </li>
              </ul>
            </dd>
          </dl>
          <div class="clear"></div>
        </section>
        <div class="js-wrap">
          <section class="fr">
            <span class="c-ccc">
              <i class="c-master f-fM">1</i>/
              <i class="c-666 f-fM">1</i>
            </span>
          </section>
          <section class="fl">
            <ol class="js-tap clearfix">
              <li :class="{'current bg-orange':VOFrontCourse.viewCountSort === 'view'}">
                <a title="关注度" href="#" @click.prevent="normalQuery('view')">关注度&nbsp;
                <span v-show="VOFrontCourse.viewCountSort === 'view'" >↓</span>
                </a>
                
              </li>
              <li :class="{'current bg-orange':VOFrontCourse.gmtCreateSort === 'create'}">
                <a title="最新" href="#" @click.prevent="normalQuery('create')">最新&nbsp;
                <span v-show="VOFrontCourse.gmtCreateSort === 'create'">↓</span>
                </a>
               
              </li>
              <li :class="{'current bg-orange':VOFrontCourse.priceSort === 'price'}">
                <a title="价格" href="#" @click.prevent="normalQuery('price')">价格&nbsp;
                  <span v-show="VOFrontCourse.priceSort === 'price'">↓</span>
                </a>
              </li>
            </ol>
          </section>
        </div>
        <div class="mt40">
          <!-- /无数据提示 开始-->
          <section v-if="this.total == 0" class="no-data-wrap">
            <em class="icon30 no-data-ico">&nbsp;</em>
            <span class="c-666 fsize14 ml10 vam">没有相关数据，小编正在努力整理中...</span>
          </section>
          <!-- /无数据提示 结束-->
          <article v-if="this.total != 0" class="comm-course-list">
            <ul class="of" id="bna">
              <li v-for="course in this.courseList" :key="course.id">
                <div class="cc-l-wrap">
                  <section class="course-img">
                    <img :src="course.cover" class="img-responsive" :alt="course.title">
                    <div class="cc-mask">
                      <a :href="'/course/'+ course.id" :title="course.title" class="comm-btn c-btn-1">开始学习</a>
                    </div>
                  </section>
                  <h3 class="hLh30 txtOf mt10">
                    <a :href="'/course/' + course.id" :title="course.title" class="course-title fsize18 c-333">{{course.title}}</a>
                  </h3>
                  <section class="mt10 hLh20 of">
                    <span v-if="Number(course.price) === 0" class="fr jgTag bg-green">
                      <i  class="c-fff fsize12 f-fA">免费</i>
                    </span>
                    <span class="fl jgAttr c-ccc f-fA">
                      <i class="c-999 f-fA">{{course.viewCount}}人学习</i>
                      |
                      <i class="c-999 f-fA">{{course.buyCount}}人购买</i>
                    </span>
                  </section>
                </div>
              </li>
            </ul>
            <div class="clear"></div>
          </article>
        </div>
        <!-- 公共分页 开始 -->
        <div v-if="courseList.length !== 0">
          <div class="paging">
            <!-- undisable这个class是否存在，取决于数据属性hasPrevious -->
            <a
              :class="{undisable: !this.hasPrevious}"
              href="#"
              title="首页"
              @click.prevent="pageQuery(1)">首</a>
            <a
              :class="{undisable: !this.hasPrevious}"
              href="#"
              title="前一页"
              @click.prevent="pageQuery(pageNum-1)">&lt;</a>
            <a v-for="page in this.pages" :key="page" :title="'第'+page+'页'" href="#"  @click.prevent="pageQuery(page)">
              {{ page }}
            </a>
            <a
              :class="{undisable: !this.hasNext}"
              href="#"
              title="后一页"
              @click.prevent="pageQuery(pageNum+1)">&gt;</a> 
            <a
              :class="{undisable: !this.hasNext}"
              href="#"
              title="末页"
              @click.prevent="pageQuery(pages)">末</a>
            <div class="clear"/>
          </div>
        </div>
        <!-- 公共分页 结束 -->
      </section>
    </section>
    <!-- /课程列表 结束 -->
  </div>
</template>
<script>
import course from '@/api/course'

export default {
  name:'coureIndex',
  data() {
    return {
      pageNum: 1,
      pageSize: 8,
      total:0,
      pages:0,
      size:0,
      hasPrevious:'',
      hasNext:'',
      courseList:[],//展示的课程数组，封装了显示的课程信息
      VOFrontCourse:{},//封装条件查询对象
      oneSubjectList:[],//一级分类列表
      twoSubjectList:[],//二级分类列表
      oneIndex: -1,
      twoIndex: -1,
    }
  },
  created() {
   
    //获取url上的参数判断是否为搜索查询
    this.getUrlParam()
    
    //调用此方法查询所有的一级分类
    this.getOneSubject()

  },
  watch:{ //vue的监听功能
    $route(to,from){ //路由变化方式，当路由发生变化，init方法就会执行
      this.getUrlParam()
    }
  },
  methods: {
    //获取url上的参数判断是否为搜索查询
    getUrlParam(){
        let urlTitle = this.$route.query.title
        // console.log(urlTitle);
        if(urlTitle){
          //必须在搜索课程之前把搜索关键字加到封装查询条件的对象中
          this.VOFrontCourse.title = urlTitle
          // console.log(this.VOFrontCourse.title)
        }

        //查询显示的课程
        this.getHtmlCourse()
        
    },
    getHtmlCourse(){
      course.getHtmlCourse(this.pageNum,this.pageSize,this.VOFrontCourse)
          .then(response => {
            //返回的信息对象
            let returnInfo = response.data.data
            //填充各个数据
            this.pageNum = returnInfo.current
            this.total = returnInfo.total
            this.pages = returnInfo.pages
            this.size = returnInfo.size
            this.hasPrevious = returnInfo.hasPrevious
            this.hasNext = returnInfo.hasNext
            this.courseList = returnInfo.lists
            
          })  
    },
    //获取一级分类
    getOneSubject(){
      course.getOneSubject()
        .then(response => {
          this.oneSubjectList = response.data.data       
        })
    },
    //根据一级分类id查询二级分类id
    getTwoSubject(subjectParentId){
      course.getTwoSubject(subjectParentId)
        .then(response => {
          this.twoSubjectList = response.data.data
        })
    },
    oneSubjectClickQuery(subjectParentId,index){
      //将传递过来的下标值赋值给oneIndex,让active样式生效
      this.oneIndex = index
      //展示二级分类内容
      this.getTwoSubject(subjectParentId)
      //填充一级分类id并进行课程查询
      this.VOFrontCourse.subjectParentId = subjectParentId
      this.getHtmlCourse()
    },
    twoSubjectClickQuery(subjectId,index){
      //将传递过来的下标值赋值给twoIndex,让active样式生效
      this.twoIndex = index
      //填充二级分类id并进行课程查询
      this.VOFrontCourse.subjectId = subjectId
      this.getHtmlCourse()
    },
    //回到全部页面
    clear(){
      //让被选中的样式消失
      this.oneIndex = -1
      this.VOFrontCourse={}
      this.courseList=[]
      this.twoSubjectList=[]
      this.getHtmlCourse()
    },
    //标准查询
    normalQuery(val){

      //判断是选择了什么查询
      if(val === 'view'){
        this.VOFrontCourse.gmtCreateSort = ''
        this.VOFrontCourse.priceSort = ''
        this.VOFrontCourse.viewCountSort = val
      }
      if(val === 'create'){
        this.VOFrontCourse.viewCountSort = ''
        this.VOFrontCourse.priceSort = ''
        this.VOFrontCourse.gmtCreateSort = val
      }
      if(val === 'price'){
        this.VOFrontCourse.gmtCreateSort = ''
        this.VOFrontCourse.viewCountSort = ''
        this.VOFrontCourse.priceSort = val
      }
      //进行课程查询
      this.getHtmlCourse()
    },
    //分页查询
    pageQuery(pageNum){
      if(pageNum > this.pages || pageNum < 1 || pageNum === this.pageNum ){
        return false;
      }
      //将输入的页数置为当前页
      this.pageNum = pageNum
      //调用方法进行查询
      this.getHtmlCourse()
    }
  },
};
</script>
