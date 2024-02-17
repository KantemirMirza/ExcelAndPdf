package com.kani.excelandpdf.util;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PageItem {
    private int numberOfPage;
    private boolean isActive;

    public PageItem(int numberOfPage, boolean isActive) {
        this.numberOfPage = numberOfPage;
        this.isActive = isActive;
    }
}
