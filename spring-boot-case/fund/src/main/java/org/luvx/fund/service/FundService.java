package org.luvx.fund.service;

import org.luvx.fund.entity.Fund;
import org.luvx.fund.mapper.FundMapper;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

@Service
public class FundService extends ServiceImpl<FundMapper, Fund> {
}
