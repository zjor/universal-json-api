package com.github.zjor.ujapi.repository;

import org.junit.Assert;
import org.junit.Test;

import static com.github.zjor.ujapi.repository.Paged.PageInfo.*;

public class PagedTest {

    @Test
    public void shouldCreatePageInfo_first() {
        var info = create(0, 10, 100);
        Assert.assertEquals(0, info.getOffset());
        Assert.assertEquals(false, info.hasPrev());
        Assert.assertEquals(true, info.hasNext());
        Assert.assertEquals(10, info.getTotalPages());
    }

    @Test
    public void shouldCreatePageInfo_second() {
        var info = create(2, 9, 100);
        Assert.assertEquals(9, info.getOffset());
        Assert.assertEquals(true, info.hasPrev());
        Assert.assertEquals(true, info.hasNext());
        Assert.assertEquals(12, info.getTotalPages());
    }

    @Test
    public void shouldCreatePageInfo_last() {
        var info = create(3, 5, 13);
        Assert.assertEquals(10, info.getOffset());
        Assert.assertEquals(true, info.hasPrev());
        Assert.assertEquals(false, info.hasNext());
        Assert.assertEquals(3, info.getTotalPages());
    }

}