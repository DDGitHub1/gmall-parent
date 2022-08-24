package com.atguigu.gmall.product.service.impl;

import com.atguigu.gmall.model.product.BaseAttrInfo;
import com.atguigu.gmall.model.product.BaseAttrValue;
import com.atguigu.gmall.product.mapper.BaseAttrValueMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.atguigu.gmall.product.service.BaseAttrInfoService;
import com.atguigu.gmall.product.mapper.BaseAttrInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Service;
import sun.rmi.runtime.Log;

import java.util.ArrayList;
import java.util.List;

/**
* @author 杜宇浩
* @description 针对表【base_attr_info(属性表)】的数据库操作Service实现
* @createDate 2022-08-23 18:36:27
*/
@Service
public class BaseAttrInfoServiceImpl extends ServiceImpl<BaseAttrInfoMapper, BaseAttrInfo>
    implements BaseAttrInfoService{

    @Autowired
    BaseAttrInfoMapper baseAttrInfoMapper;
    @Autowired
    BaseAttrValueMapper baseAttrValueMapper;
    @Override
    public List<BaseAttrInfo> getAttrInfoAndValueByCategoryId(Long c1Id, Long c2Id, Long c3Id) {
        List<BaseAttrInfo> infos = baseAttrInfoMapper.getAttrInfoAndValueByCategoryId(c1Id,c2Id,c3Id);
        return infos;
    }

    @Override
    public void saveAttrInfo(BaseAttrInfo baseAttrInfo) {
        Long id = baseAttrInfo.getId();
        if(id==null){
            addBaseAttrInfo(baseAttrInfo);
        }else {
            updateBaseAttrInfo(baseAttrInfo);
        }
    }
    private void updateBaseAttrInfo(BaseAttrInfo info){
        //2.1）、改属性名信息
        baseAttrInfoMapper.updateById(info);
        //2.2）、改属性值
        //以前的值：2019、2020、2021、2022
        //现在前端：2019以前、2021、2023以后
        //1、老记录全删，新提交全新增。导致引用失效
        //2、正确做法：

        List<BaseAttrValue> valueList = info.getAttrValueList();
        //先删除
        List<Long> vids = new ArrayList<>();
        for (BaseAttrValue baseAttrValue : valueList) {
            Long attrId = baseAttrValue.getAttrId();
            if(attrId!=null){
                vids.add(attrId);
            }
        }
        if(vids.size()>0){
            //部分删除
            QueryWrapper<BaseAttrValue> deleteWrapper = new QueryWrapper<>();
            deleteWrapper.eq("attr_id",info.getId());
            deleteWrapper.notIn("attr_id",info.getId());
            baseAttrValueMapper.delete(deleteWrapper);
        }else {
            //全删除 前端一个属性值id都没带 把这个属性id下的所有属性值都删除
            QueryWrapper<BaseAttrValue> deleteWrapper = new QueryWrapper<>();
            deleteWrapper.eq("attr_id",info.getId());
            baseAttrValueMapper.delete(deleteWrapper);
        }

        for (BaseAttrValue baseAttrValue : valueList) {
            //修改属性值
            if(baseAttrValue.getId()!=null){
                //属性值有id 说明数据库以前有，此次只需要修改即可
                baseAttrValueMapper.updateById(baseAttrValue);
            }
            if(baseAttrValue.getId()==null){
                //说明数据库以前没有是新增
                baseAttrValue.setAttrId(info.getId());
                baseAttrValueMapper.insert(baseAttrValue);
            }
        }
    }

    private void addBaseAttrInfo(BaseAttrInfo info){
        //1 保存属性名
        baseAttrInfoMapper.insert(info);
        Long id = info.getId();
        //2 保存属性值
        List<BaseAttrValue> valueList = info.getAttrValueList();
        for (BaseAttrValue baseAttrValue : valueList) {
            baseAttrValue.setAttrId(id);
            baseAttrValueMapper.insert(baseAttrValue);
        }
    }
}




