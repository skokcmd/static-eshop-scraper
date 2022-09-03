package com.skokcmd.service;

import java.io.IOException;
import java.util.List;

public interface IScraperService<T> {

    List<T> getItemsByClass(String className) throws IOException;

}
