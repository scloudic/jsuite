package com.scloudic.jsuite.sysuser.mgr.web.controllers;

import com.scloudic.jsuite.core.utils.Enums;
import com.scloudic.jsuite.log.annotation.Log;
import com.scloudic.jsuite.sysuser.mgr.entity.SysDept;
import com.scloudic.jsuite.sysuser.mgr.service.SysDeptService;
import com.scloudic.jsuite.sysuser.mgr.service.SysUserService;
import com.scloudic.jsuite.sysuser.mgr.web.model.DeptVo;
import com.scloudic.rabbitframework.core.exceptions.BizException;
import com.scloudic.rabbitframework.core.utils.BeanUtils;
import com.scloudic.rabbitframework.core.utils.StringUtils;
import com.scloudic.rabbitframework.jbatis.mapping.param.Criteria;
import com.scloudic.rabbitframework.jbatis.mapping.param.Where;
import com.scloudic.rabbitframework.security.authz.annotation.UriPermissions;
import com.scloudic.rabbitframework.web.AbstractRabbitController;
import com.scloudic.rabbitframework.web.Result;
import com.scloudic.rabbitframework.web.annotations.FormValid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 部门管理
 */
@RestController
@RequestMapping("/jsuite/deptMgr")
public class DeptController extends AbstractRabbitController {
    @Autowired
    private SysDeptService sysDeptService;
    @Autowired
    private SysUserService sysUserService;

    /**
     * 树型结构获取部门所有信息
     *
     * @param deptId
     * @return
     */
    @GetMapping("deptTree")
    @UriPermissions
    @Log(operatorType = Log.OperateType.SELECT, remark = "树型结构获取部门所有信息")
    public Result<List<DeptVo>> deptTree(@RequestParam(value = "deptId",
            required = false, defaultValue = "0") Integer deptId) {
        List<DeptVo> result = new ArrayList<DeptVo>();
        result = findChildren(deptId, deptId);
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
     * @param deptName 部门名称
     * @return json
     */
    @GetMapping("listAll")
    @UriPermissions
    @Log(operatorType = Log.OperateType.SELECT, remark = "查询所有部门信息")
    public Result<List<SysDept>> list(@RequestParam(value = "deptName", required = false)
                                              String deptName) {
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

    @PostMapping("addDept")
    @FormValid(fieldFilter = {"deptId", "activeStatus"})
    @UriPermissions
    @Log(operatorType = Log.OperateType.ADD, remark = "添加部门")
    public Result<Object> addDept(@RequestBody SysDept sysDept) {
        if (sysDept.getSortNum() == null) {
            sysDept.setSortNum(0);
        }
        Date date = new Date();
        sysDept.setDelStatus(Enums.DelStatus.NORMAL.getValue());
        sysDept.setActiveStatus(Enums.ActiveStatus.OPEN.getValue());
        sysDept.setCreateTime(date);
        sysDept.setUpdateTime(date);
        sysDeptService.insertByEntity(sysDept);
        SysDept dept = sysDeptService.selectById(sysDept.getDeptParentId());
        String deptIds = dept.getDeptIds();
        String deptNames = sysDept.getDeptName();
        if (sysDept.getDeptParentId().intValue() != 1) {
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


    @PostMapping("updateDept")
    @FormValid(fieldFilter = {"activeStatus"})
    @UriPermissions
    @Log(operatorType = Log.OperateType.ADD, remark = "修改部门")
    public Result updateDept(@RequestBody SysDept sysDept) {
        Date date = new Date();
        sysDept.setUpdateTime(date);
        sysDeptService.updateDept(sysDept);
        return success(sysDept.getDeptId());
    }

    @PostMapping("updateActiveStatus")
    @FormValid(fieldFilter = {"deptParentId", "deptName"})
    @UriPermissions
    @Log(operatorType = Log.OperateType.UPDATE, remark = "修改部门启用状态")
    public Result updateActiveStatus(@RequestBody SysDept dept) {
        SysDept sysDept = new SysDept();
        sysDept.setDeptId(dept.getDeptId());
        if (Enums.ActiveStatus.getActiveStatus(dept.getActiveStatus()) == null) {
            throw new BizException("activeStatus.null");
        }
        Where where = new Where();
        Criteria criteria = where.createCriteria();
        criteria.andEqual(SysDept::getDeptParentId, dept.getDeptId());
        criteria.andEqual(SysDept::getDelStatus, Enums.DelStatus.NORMAL.getValue());
        long count = sysDeptService.selectCountByParams(where);
        if (count > 0) {
            throw new BizException("dept.haveChildrenDept.error");
        }

        sysDept.setActiveStatus(dept.getActiveStatus());
        sysDept.setUpdateTime(new Date());
        sysDeptService.updateByEntity(sysDept);
        return success();
    }

    @PostMapping("del")
    @UriPermissions
    @FormValid(fieldFilter = {"deptParentId", "deptName", "activeStatus"})
    @Log(operatorType = Log.OperateType.DEL, remark = "删除部门信息")
    public Result del(@RequestBody SysDept sysDept) {
        Integer deptId = sysDept.getDeptId();
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
        SysDept updateDept = new SysDept();
        updateDept.setDeptId(deptId);
        updateDept.setDelStatus(Enums.DelStatus.DEL.getValue());
        sysDeptService.updateByEntity(sysDept);
        return success();
    }
}
