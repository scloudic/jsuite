package com.scloudic.jsuite.sysuser.mgr.service.impl;

import com.scloudic.jsuite.sysuser.mgr.entity.SysMenu;
import com.scloudic.jsuite.sysuser.mgr.entity.SysRoleMenu;
import com.scloudic.jsuite.sysuser.mgr.entity.SysUserRole;
import com.scloudic.jsuite.sysuser.mgr.mapper.SysMenuMapper;
import com.scloudic.jsuite.sysuser.mgr.mapper.SysRoleMenuMapper;
import com.scloudic.jsuite.sysuser.mgr.service.SysMenuService;
import com.scloudic.rabbitframework.core.exceptions.BizException;
import com.scloudic.rabbitframework.core.utils.StringUtils;
import com.scloudic.rabbitframework.jbatis.mapping.lambda.SFunctionUtils;
import com.scloudic.rabbitframework.jbatis.mapping.param.Criteria;
import com.scloudic.rabbitframework.jbatis.mapping.param.Where;
import com.scloudic.rabbitframework.jbatis.service.IServiceImpl;
import com.scloudic.rabbitframework.core.utils.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 功能菜单实现类
 */
@Service
public class SysMenuServiceImpl extends
        IServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {
    @Autowired
    private SysMenuMapper sysMenuMapper;
    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    public SysMenuMapper getBaseMapper() {
        return sysMenuMapper;
    }

    @Override
    public List<SysMenu> findChildMenuByParentMenuId(String parentMenuId, Integer menuBtnFlag) {
        Where where = new Where();
        Criteria criteria = where.createCriteria();
        criteria.andEqual(SysMenu::getParentMenuId, parentMenuId);
        criteria.andEqual(menuBtnFlag != null, SysMenu::getBtnFlag, menuBtnFlag);
        where.setOrderBy(SysMenu::getSortNum, Where.OrderByType.DESC);
        return sysMenuMapper.selectByParams(where);
    }

    /**
     * 保存菜单
     *
     * @param menu
     * @return
     */
    @Transactional
    @Override
    public int saveMenu(SysMenu menu) {
        return sysMenuMapper.insertByEntity(menu);

    }

    /**
     * 修改菜单
     *
     * @param menu
     * @return
     */
    @Transactional
    @Override
    public int updateMenu(SysMenu menu) {
        return sysMenuMapper.updateByEntity(menu);
    }

    /**
     * 删除菜单,级联删除角色菜单{@link SysRoleMenu} 中的信息
     *
     * @param menuId
     * @return
     */
    @Transactional
    @Override
    public int delMenu(String menuId) {
        if (StringUtils.isBlank(menuId)) {
            throw new BizException("menuId is null");
        }
        Where paramType = new Where();
        Criteria criteria = paramType.createCriteria();
        criteria.andEqual(SysMenu::getParentMenuId, menuId);
        long count = sysMenuMapper.selectCountByParams(paramType);
        if (count > 0) {
            throw new BizException("have.child.menu");
        }
        Where delWhere = new Where();
        Criteria delCriteria = delWhere.createCriteria();
        delCriteria.andEqual(SysMenu::getSysMenuId, menuId);
        sysRoleMenuMapper.deleteByParams(delWhere);
        return sysMenuMapper.deleteById(menuId);
    }

    /**
     * 根据角色主键获取角色菜单
     *
     * @param roleIds      必传
     * @param btnFlag      可空
     * @param parentMenuId 可空
     * @return
     */
    @Override
    public List<SysMenu> findMenuByRole(List<Long> roleIds, String parentMenuId, Integer btnFlag) {
        if (CollectionUtils.isEmpty(roleIds)) {
            throw new NullPointerException("roleIds is null");
        }
        Where where = new Where();
        Criteria criteria = where.createCriteria();
        String roleIdField = SFunctionUtils.getFieldName(SysRoleMenu::getSysRoleId);
        criteria.andIn("rm." + roleIdField, roleIds);
        if (btnFlag != null) {
            String btnFlagField = SFunctionUtils.getFieldName(SysMenu::getBtnFlag);
            criteria.andEqual("menu." + btnFlagField, btnFlag);
        }
        if (parentMenuId != null) {
            String parentMenuIdField = SFunctionUtils.getFieldName(SysMenu::getParentMenuId);
            criteria.andEqual("menu." + parentMenuIdField, parentMenuId);
        }
        String sortNumField = SFunctionUtils.getFieldName(SysMenu::getSortNum);
        where.setOrderBy("menu." + sortNumField);
        return sysRoleMenuMapper.findMenuJoinRoleMenuByParams(where);
    }

    @Override
    public Integer getCountMenuByRole(List<Long> roleIds, String parentMenuId, Integer btnFlag) {
        if (CollectionUtils.isEmpty(roleIds)) {
            throw new NullPointerException("roleIds is null");
        }
        Where where = new Where();
        Criteria criteria = where.createCriteria();
        String roleIdField = SFunctionUtils.getFieldName(SysRoleMenu::getSysRoleId);
        criteria.andIn("rm." + roleIdField, roleIds);
        if (btnFlag != null) {
            String btnFlagField = SFunctionUtils.getFieldName(SysMenu::getBtnFlag);
            criteria.andEqual("menu." + btnFlagField, btnFlag);
        }
        if (parentMenuId != null) {
            String parentMenuIdField = SFunctionUtils.getFieldName(SysMenu::getParentMenuId);
            criteria.andEqual("menu." + parentMenuIdField, parentMenuId);
        }
        return sysRoleMenuMapper.getCountMenuJoinRoleMenuByParams(where);
    }

    @Override
    public List<SysMenu> findUserRoleMenuByUserId(String userId, Integer btnFlag) {
        Where whereParamType = new Where();
        Criteria criteria = whereParamType.createCriteria();
        criteria.andEqual("ur." + SysUserRole.SYS_USER_ID, userId);
        if (btnFlag != null) {
            String btnFlagField = SFunctionUtils.getFieldName(SysMenu::getBtnFlag);
            criteria.andEqual("menu." + btnFlagField, btnFlag);
        }
        return sysRoleMenuMapper.findUserRoleMenuByParams(whereParamType);
    }
}
