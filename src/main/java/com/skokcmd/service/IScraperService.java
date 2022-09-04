package com.skokcmd.service;

import java.io.IOException;
import java.util.List;

public interface IScraperService<T> {

    List<T> getItemsByCssClassName(String className) throws IOException;

}
