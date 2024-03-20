package com.booksearch.dto.book.naver;

import com.booksearch.deserializer.xml.LocalDateAdapter;
import jakarta.xml.bind.annotation.*;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.Getter;

import java.time.LocalDate;

@XmlRootElement(name = "rss")
@XmlAccessorType(XmlAccessType.FIELD)
@Getter
public class NaverXmlBook {

    @XmlAttribute
    private String version;

    @XmlElement(name = "channel")
    private Channel channel;

    @XmlAccessorType(XmlAccessType.FIELD)
    @Getter
    public static class Channel {

        @XmlElement(name = "title")
        private String title;

        @XmlElement(name = "link")
        private String link;

        @XmlElement(name = "description")
        private String description;

        @XmlElement(name = "lastBuildDate")
        private String lastBuildDate;

        @XmlElement(name = "total")
        private int total;

        @XmlElement(name = "start")
        private int start;

        @XmlElement(name = "display")
        private int display;

        @XmlElement(name = "item")
        private Item item;

        @Getter
        public static class Item {

            @XmlElement(name = "title")
            private String title;

            @XmlElement(name = "link")
            private String link;

            @XmlElement(name = "image")
            private String image;

            @XmlElement(name = "author")
            private String author;

            @XmlElement(name = "price")
            private int price;

            @XmlElement(name = "discount")
            private int discount;

            @XmlElement(name = "publisher")
            private String publisher;

            @XmlElement(name = "pubdate")
            @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
            private LocalDate pubdate;

            @XmlElement(name = "isbn")
            private String isbn;

            @XmlElement(name = "description")
            private String description;

        }
    }
}


