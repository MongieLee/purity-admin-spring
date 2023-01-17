package cn.mgl.purity.excelConverter.todayNews;

import com.alibaba.excel.converters.Converter;

public class publishStatusConverter implements Converter<Boolean> {
    @Override
    public Class<?> supportJavaTypeKey() {
        return Boolean.class;
    }


}
