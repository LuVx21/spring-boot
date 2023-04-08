package org.luvx.boot.web.entity.validate.custom;

import java.util.List;

import com.google.common.collect.Lists;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;
import org.luvx.boot.web.entity.validate.ValidationVo;
import org.luvx.boot.web.entity.validate.ValidationVo.AddGroup;
import org.luvx.boot.web.entity.validate.ValidationVo.FemaleGroup;
import org.luvx.boot.web.entity.validate.ValidationVo.MaleGroup;
import org.luvx.boot.web.entity.validate.ValidationVo.UpdateGroup;

import lombok.extern.slf4j.Slf4j;

/**
 * https://blog.csdn.net/f641385712/article/details/99725482
 */
@Slf4j
public class VoProvider implements DefaultGroupSequenceProvider<ValidationVo> {
    @Override
    public List<Class<?>> getValidationGroups(ValidationVo vo) {
        List<Class<?>> result = Lists.newArrayList(ValidationVo.class);
        if (vo == null) {
            return result;
        }
        result.add(
                vo.getId() != null ? UpdateGroup.class : AddGroup.class
        );

        if (StringUtils.isNotEmpty(vo.getSexName())) {
            result.add(
                    "男".equals(vo.getSexName()) ? MaleGroup.class : FemaleGroup.class
            );
        }
        return result;
    }
}
