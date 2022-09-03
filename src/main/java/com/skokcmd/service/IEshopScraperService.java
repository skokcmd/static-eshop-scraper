package com.skokcmd.service;

import com.skokcmd.Product;
import org.jsoup.nodes.Element;

public interface IEshopScraperService extends IScraperService<Product> {

    String getItemTextByUniqueTag(Element item, String tag);

    String getItemTextFromByClassName(Element item, String className);

    String getItemImgSrc(Element item, String imgSrcAtr);

}
