package dev.necron.tokens.bukkit.message;

import dev.necron.tokens.bukkit.message.category.MessageCategory;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;

import java.util.function.Supplier;

@RequiredArgsConstructor(staticName = "of")
@Getter
public class Message {

    private final String value;
    private final MessageCategory category;

    public void execute(Player player, Supplier<String> supplier) {
        String message = supplier.get();
        category.execute(player, message);
    }

}
