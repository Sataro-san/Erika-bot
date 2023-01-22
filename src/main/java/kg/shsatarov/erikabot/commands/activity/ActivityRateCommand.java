package kg.shsatarov.erikabot.commands.activity;

import kg.shsatarov.erikabot.commands.ExecutableCommand;
import kg.shsatarov.erikabot.entities.ActivityBalanceRate;
import kg.shsatarov.erikabot.services.ActivityBalanceRateService;
import kg.shsatarov.erikabot.utils.StringFormatter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ActivityRateCommand implements ExecutableCommand {

    private final ActivityBalanceRateService activityBalanceRateService;

    @Override
    public String getName() {
        return "activity-rates";
    }

    @Override
    public String getDescription() {
        return "Тарификация по Активностям";
    }

    @Override
    public void execute(SlashCommandInteractionEvent slashCommandEvent) {

        List<ActivityBalanceRate> activityRates = activityBalanceRateService.getAll();

        StringBuilder activityRatesStringBuilder = new StringBuilder();

        for (ActivityBalanceRate activityRate : activityRates) {
            activityRatesStringBuilder.append(StringFormatter.format("**{}** : ***{}*** :dollar:\n", activityRate.getApplicationName(), activityRate.getRate()));
        }

        slashCommandEvent
                .reply(StringFormatter.format("{}\n:video_game: Начисления по Активностям:\n{}\n*Валюта начисляется каждые 30 секунд*", slashCommandEvent.getMember().getAsMention(), activityRatesStringBuilder.toString()))
                .queue();
    }
}