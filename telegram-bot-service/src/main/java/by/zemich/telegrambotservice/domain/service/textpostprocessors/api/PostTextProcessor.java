package by.zemich.telegrambotservice.domain.service.textpostprocessors.api;


import by.zemich.telegrambotservice.domain.model.KufarAdvertisement;

public interface PostTextProcessor {
    String process(KufarAdvertisement kufarAdvertisement);

    boolean isApplicable(KufarAdvertisement kufarAdvertisement);

    static String getItalicHtmlStyle(String source) {
        return "<i>" + source + "</i>";
    }

    static String getBoldHtmlStyle(String source) {
        return "<b>" + source + "</b>";
    }

    static String getHtmlLink(String targetLink, String textLink) {
        return """
                <a href="%s">%s</a>
                """.formatted(targetLink, textLink);
    }

    static String getTag(String source) {
        return "#" + source.trim()
                .replaceAll("&", "and")
                .replaceAll(",","")
                .replaceAll("\\s+","_");
    }

}
