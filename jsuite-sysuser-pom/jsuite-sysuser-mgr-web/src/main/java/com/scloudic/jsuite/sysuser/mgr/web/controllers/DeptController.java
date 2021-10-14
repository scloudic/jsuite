package com.scloudic.jsuite.sysuser.mgr.web.controllers;

import com.scloudic.jsuite.core.utils.Enums;
import com.scloudic.jsuite.log.annotation.Log;
import com.scloudic.jsuite.sysuser.mgr.entity.SysDept;
import com.scloudic.jsuite.sysuser.mgr.entity.SysUser;
import com.scloudic.jsuite.sysuser.mgr.service.SysDeptService;
import com.scloudic.jsuite.sysuser.mgr.service.SysUserService;
import com.scloudic.jsuite.sysuser.mgr.web.vo.DeptVo;
import com.scloudic.rabbitframework.core.exceptions.BizException;
import com.scloudic.rabbitframework.core.utils.BeanUtils;
import com.scloudic.rabbitframework.core.utils.StringUtils;
import com.scloudic.rabbitframework.jbatis.mapping.param.Criteria;
import com.scloudic.rabbitframework.jbatis.mapping.param.Where;
import com.scloudic.rabbitframework.security.authz.annotation.UriPermissions;
import com.scloudic.rabbitframework.web.AbstractContextResource;
import com.scloudic.rabbitframework.web.Result;
import com.scloudic.rabbitframework.web.annotations.FormValid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.inject.Singleton;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotBlank;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 部门管理
 */
@Component
@Path("/jsuite/deptMgr")
@Singleton
public class DeptController extends AbstractContextResource {
    @Autowired
    private SysDeptService sysDeptService;
    @Autowired
    private SysUserService sysUserService;

    /**
     * 树型结构获取部门所有信息
     *
     * @param request request
     * @param userId  用户主键
     * @return json
     */
    @GET
    @Path("deptTree")
    @UriPermissions
    @Log(operatorType = Log.OperateType.SELECT, remark = "树型结构获取部门所有信息")
    public Result<List<DeptVo>> deptTree(@Context HttpServletRequest request,
                                         @NotBlank @QueryParam("userId") String userId,
                                         @DefaultValue("0") @QueryParam("deptId") Integer deptId) {
        List<DeptVo> result = new ArrayList<DeptVo>();
        Integer selectDeptId = 0;
        if (StringUtils.isNotBlank(userId)) {
            SysUser sysUser = sysUserService.selectById(userId);
            if (sysUser != null) {
                selectDeptId = sysUser.getDeptId();
            }
        }
        result = findChildren(deptId, selectDeptId);
        return success(result);
    }

    private List<DeptVo> findChildren(Integer deptId, Integer selectDeptId) {
        List<DeptVo> result = new ArrayList<DeptVo>();
        Where where = new Where();
        where.createCriteria().andEqual(SysDept::getDeptParentId, deptId);
        where.setOrderBy(SysDept::getSortNum);
        List<SysDept> sysDepts = sysDeptService.selectByParams(where);
        for (SysDept dept : sysDepts) {
            Integer sysDeptId = dept.getDeptId();
            DeptVo deptVo = new DeptVo();
            BeanUtils.copyProperties(deptVo, dept);
            where.clear();
            where.createCriteria().andEqual(SysDept::getDeptParentId, sysDeptId);
            where.setOrderBy(SysDept::getSortNum);
            if (dept.getDeptId().intValue() == selectDeptId.intValue()) {
                deptVo.setCheckStatus(true);
            }
            Long childMenuNum = sysDeptService.selectCountByParams(where);
            if (childMenuNum > 0) {
                deptVo.setChildren(findChildren(sysDeptId, selectDeptId));
            } else {
                deptVo.setChildren(new ArrayList<DeptVo>());
            }
            result.add(deptVo);
        }
        return result;
    }


    /**
     * 查询所有部门信息
     *
     * @param request  request
     * @param deptName 部门名称
     * @return json
     */
    @GET
    @Path("listAll")
    @UriPermissions
    @Log(operatorType = Log.OperateType.SELECT, remark = "查询所有部门信息")
    public Result<List<SysDept>> list(@Context HttpServletRequest request,
                                      @QueryParam("deptName") String deptName) {
        Where where = new Where();
        Criteria criteria = where.createCriteria();
        criteria.andEqual(SysDept::getDelStatus, Enums.DelStatus.NORMAL.getValue());
        if (StringUtils.isNotBlank(deptName)) {
            criteria.andLike(SysDept::getDeptName, "%" + deptName + "%");
        }
        where.setOrderBy(SysDept::getSortNum);
        List<SysDept> sysDepts = sysDeptService.selectByParams(where);
        return success(sysDepts);
    }

    @POST
    @Path("addDept")
    @FormValid
    @UriPermissions
    @Log(operatorType = Log.OperateType.ADD, remark = "添加部门")
    public Result<Object> addDept(@Context HttpServletRequest request,
                                  @NotBlank @FormParam("deptName") String deptName,
                                  @NotBlank @FormParam("deptParentId") Integer deptParentId,
                                  @FormParam("deptLead") String deptLead,
                                  @FormParam("deptPhone") String deptPhone,
                                  @FormParam("deptMail") String deptMail,
                                  @DefaultValue("0") @FormParam("sortNum") Integer sortNum,
                                  @FormParam("remark") String remark) {
        SysDept sysDept = new SysDept();
        sysDept.setDeptParentId(deptParentId);
        sysDept.setDeptName(deptName);
        sysDept.setDeptLead(deptLead);
        sysDept.setDeptPhone(deptPhone);
        sysDept.setDeptMail(deptMail);
        sysDept.setSortNum(sortNum);
        Date date = new Date();
        sysDept.setDelStatus(Enums.DelStatus.NORMAL.getValue());
        sysDept.setActiveStatus(Enums.ActiveStatus.OPEN.getValue());
        sysDept.setCreateTime(date);
        sysDept.setUpdateTime(date);
        sysDept.setRemark(remark);
        sysDeptService.insertByEntity(sysDept);
        SysDept dept = sysDeptService.selectById(deptParentId);
        String deptIds = dept.getDeptIds();
        String deptNames = sysDept.getDeptName();
        if (deptParentId.intValue() != 1) {
            deptNames = dept.getDeptNames();
            deptNames = deptNames + "," + sysDept.getDeptName();
        }
        deptIds = deptIds + "," + sysDept.getDeptId();
        SysDept updateDept = new SysDept();
        updateDept.setDeptId(sysDept.getDeptId());
        updateDept.setDeptIds(deptIds);
        updateDept.setDeptNames(deptNames);
        sysDeptService.updateByEntity(updateDept);
        return success();
    }


    @POST
    @Path("updateDept")
    @FormValid
    @UriPermissions
    @Log(operatorType = Log.OperateType.ADD, remark = "修改部门")
    public Result updateDept(@Context HttpServletRequest request,
                             @NotBlank @FormParam("deptName") String deptName,
                             @NotBlank @FormParam("deptId") Integer deptId,
                             @FormParam("deptLead") String deptLead,
                             @FormParam("deptPhone") String deptPhone,
                             @FormParam("deptMail") String deptMail,
                             @DefaultValue("0") @FormParam("sortNum") Integer sortNum,
                             @FormParam("remark") String remark) {
        SysDept sysDept = new SysDept();
        sysDept.setDeptId(deptId);
        sysDept.setDeptName(deptName);
        sysDept.setDeptLead(deptLead);
        sysDept.setDeptPhone(deptPhone);
        sysDept.setDeptMail(deptMail);
        sysDept.setSortNum(sortNum);
        Date date = new Date();
        sysDept.setUpdateTime(date);
        sysDept.setRemark(remark);
        sysDeptService.updateDept(sysDept);
        return success();
    }

    @POST
    @Path("updateActiveStatus")
    @FormValid
    @UriPermissions
    @Log(operatorType = Log.OperateType.UPDATE, remark = "修改部门启用状态")
    public Result updateActiveStatus(@Context HttpServletRequest request,
                                     @NotBlank @FormParam("deptId") Integer deptId,
                                     @NotBlank @FormParam("activeStatus") Integer activeStatus) {
        SysDept sysDept = new SysDept();
        sysDept.setDeptId(deptId);
        if (Enums.ActiveStatus.getActiveStatus(activeStatus) == null) {
            throw new BizException("activeStatus.null");
        }
        Where where = new Where();
        Criteria criteria = where.createCriteria();
        criteria.andEqual(SysDept::getDeptParentId, deptId);
        criteria.andEqual(SysDept::getDelStatus, Enums.DelStatus.NORMAL.getValue());
        long count = sysDeptService.selectCountByParams(where);
        if (count > 0) {
            throw new BizException("dept.haveChildrenDept.error");
        }

        sysDept.setActiveStatus(activeStatus);
        sysDept.setUpdateTime(new Date());
        sysDeptService.updateByEntity(sysDept);
        return success();
    }

    @POST
    @Path("del")
    @UriPermissions
    @FormValid
    @Log(operatorType = Log.OperateType.DEL, remark = "删除部门信息")
    public Result del(@Context HttpServletRequest request,
                      @NotBlank @FormParam("deptId") Integer deptId) {
        if (deptId.intValue() == 1) {
            throw new BizException("机构部门不能删除!");
        }
        Where where = new Where();
        where.createCriteria()
                .andEqual(SysDept::getDeptParentId, deptId)
                .andEqual(SysDept::getDelStatus, Enums.DelStatus.NORMAL.getValue());
        long count = sysDeptService.selectCountByParams(where);
        if (count > 0) {
            throw new BizException("还有子部门不能删除!");
        }
        SysDept sysDept = new SysDept();
        sysDept.setDeptId(deptId);
        sysDept.setDelStatus(Enums.DelStatus.DEL.getValue());
        sysDeptService.updateByEntity(sysDept);
        return success();
    }
}
