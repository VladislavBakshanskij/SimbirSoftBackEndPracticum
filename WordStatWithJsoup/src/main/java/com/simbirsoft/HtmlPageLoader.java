package com.simbirsoft;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class HtmlPageLoader {
    private static final Logger logger = LogManager.getLogger(HtmlPageLoader.class);
    private String url;

    public HtmlPageLoader(@NotNull String url) {
        this.url = url;
    }

    @NotNull
    public String getUrl() {
        return url;
    }

    @Nullable
    public Document load() {
        try {
            return Jsoup.connect(url).get();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }
}
