<template>
  <div class="cart py-container">
    <!--主内容-->
    <div class="checkout py-container  pay">
      <div class="checkout-tit">
        <h4 class="fl tit-txt"><span class="success-icon"></span><span class="success-info">订单提交成功，请您及时付款！订单号：{{payObj.out_trade_no}}</span>
        </h4>
        <span class="fr"><em class="sui-lead">应付金额：</em><em class="orange money">￥{{payObj.total_fee}}</em></span>
        <div class="clearfix"></div>
      </div>
      <div class="checkout-steps">
        <div class="fl weixin">微信支付</div>
        <div class="fl sao">
          <p class="red">请使用微信扫一扫。</p>
          <div class="fl code">
            <!-- <img id="qrious" src="~/assets/img/erweima.png" alt=""> -->
            <!-- <qriously value="weixin://wxpay/bizpayurl?pr=R7tnDpZ" :size="338"/> -->
            <qriously :value="payObj.code_url" :size="338"/>
            <div class="saosao">
              <p>请使用微信扫一扫</p>
              <p>扫描二维码支付</p>
            </div>

          </div>

        </div>
        <div class="clearfix"></div>
        <!-- <p><a href="pay.html" target="_blank">> 其他支付方式</a></p> -->
        
      </div>
    </div>
  </div>
</template>

<script>
import order from '@/api/order'
export default {
    name:'pay',
    created() {
        //获取url中的参数
        this.pid = this.$route.params.pid
        //调用方法创建支付二维码
        this.createOrderCode()
    },
    data(){
        return{
            pid:'',
            payObj:{},
            check:'',

        }
    },
    methods: {
        createOrderCode(){
          order.createOrderCode(this.pid)
            .then(response => {
              //填充页面数据
              this.payObj = response.data.data

              //定时器检测订单状态
              this.queryOrderPayStatus()
            })
        },
        //使用定时器检测订单支付状态
        queryOrderPayStatus(){
          this.check = setInterval(() => {
            order.queryOrderPayStatus(this.pid)
              .then(response => {
                if(response.data.code === 20000){
                   //清除定时器
                   clearInterval(this.check);
                   //提示
                   this.$message.success("支付成功！")
                   //跳转回课程详情页
                   this.$router.push({path:'/course/' + this.payObj.course_id})
                }
              }) 
          }, 3000);
        }
    },
    //利用vue的生命周期机制，在vue对象销毁之前清除定时器
    beforeDestroy() {
      if(this.check) {
        clearInterval(this.check)
      }
    }
}
</script>