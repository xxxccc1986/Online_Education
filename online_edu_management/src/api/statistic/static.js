import request from '@/utils/request'

export default{
    //创建数据
    countRegister(day){
        return request({
            url:`/servicedaily/countNum/${day}`,
            method:'post'
        })
    },
    //获取图表显示需要的数据
    showData(seacheObj){
        return request({
            url:`/servicedaily/showTableData/${seacheObj.type}/${seacheObj.begin}/${seacheObj.end}`,
            method:'get'
        })
    }
}