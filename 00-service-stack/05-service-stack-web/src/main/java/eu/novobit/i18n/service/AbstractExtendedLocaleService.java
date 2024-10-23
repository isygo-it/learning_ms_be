package eu.novobit.i18n.service;

import eu.novobit.model.extendable.LocaleMessageModel;
import eu.novobit.repository.MessageModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.Optional;

/**
 * The type Abstract extended locale service.
 */
public abstract class AbstractExtendedLocaleService implements ExtendedLocaleService {


    @Qualifier("extendedMessageMap")
    @Autowired
    private Map<String, String> extendedMessageMap;

    @Override
    public final String getMessage(String code, String locale) {
        String message = extendedMessageMap.get(code + "|" + locale);
        if (!StringUtils.hasText(message)) {
            message = loadMessage(code, locale);
            if (StringUtils.hasText(message)) {
                extendedMessageMap.put(code + "|" + locale, message);
            }
        }
        return message;
    }

    @Override
    public final void clear() {
        extendedMessageMap.clear();
    }

    @Override
    public final void refresh() {
        extendedMessageMap.forEach((code, message) -> {
            String[] codePlisLocal = code.split("|");
            message = loadMessage(codePlisLocal[0], codePlisLocal[1]);
        });
    }

    @Override
    public String loadMessage(String code, String locale) {
        Optional<LocaleMessageModel> optionalMessage = getMessageRepository().findByCodeAndLocale(code, locale);
        if (optionalMessage.isPresent()) {
            return optionalMessage.get().getText();
        }
        return null;
    }

    @Override
    public void setMessage(String code, String locale, String message) {
        extendedMessageMap.put(code + "|" + locale, message);
        Optional<LocaleMessageModel> optionalMessage = getMessageRepository().findByCodeAndLocale(code, locale);
        if (optionalMessage.isPresent()) {
            optionalMessage.get().setText(message);
            getMessageRepository().save(optionalMessage.get());
        } else {
            getMessageRepository().save(LocaleMessageModel.builder()
                    .code(code)
                    .locale(locale)
                    .text(message)
                    .build());
        }
    }

    /**
     * Gets message repository.
     *
     * @return the message repository
     */
    public abstract MessageModelRepository getMessageRepository();

    @Override
    public boolean enabled() {
        return true;
    }
}
