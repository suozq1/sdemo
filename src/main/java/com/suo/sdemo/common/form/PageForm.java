package com.suo.sdemo.common.form;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public class PageForm {
	private Page<?> page;

	public Page<?> getPage() {
		return page;
	}

	public void setPage(Page<?> page) {
		this.page = page;
	}
}
