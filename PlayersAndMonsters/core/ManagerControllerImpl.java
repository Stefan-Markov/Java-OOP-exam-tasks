package PlayersAndMonsters.core;

import PlayersAndMonsters.common.ConstantMessages;
import PlayersAndMonsters.core.interfaces.ManagerController;
import PlayersAndMonsters.models.battleFields.BattleFieldImpl;
import PlayersAndMonsters.models.battleFields.interfaces.Battlefield;
import PlayersAndMonsters.models.cards.MagicCard;
import PlayersAndMonsters.models.cards.TrapCard;
import PlayersAndMonsters.models.cards.interfaces.Card;
import PlayersAndMonsters.models.players.Advanced;
import PlayersAndMonsters.models.players.Beginner;
import PlayersAndMonsters.models.players.interfaces.Player;
import PlayersAndMonsters.repositories.CardRepositoryImpl;
import PlayersAndMonsters.repositories.PlayerRepositoryImpl;
import PlayersAndMonsters.repositories.interfaces.CardRepository;
import PlayersAndMonsters.repositories.interfaces.PlayerRepository;


public class ManagerControllerImpl implements ManagerController {

    private PlayerRepository playerRepository;
    private CardRepository cardRepository;
    private Battlefield battlefield;

    public ManagerControllerImpl() {
        this.playerRepository = new PlayerRepositoryImpl();
        this.cardRepository = new CardRepositoryImpl();
        this.battlefield = new BattleFieldImpl();
    }
    @Override
    public String addPlayer(String type, String username) {
        Player player;
        if (type.equals("Beginner")) {
            player = new Beginner(new CardRepositoryImpl(), username);
        } else {
            player = new Advanced(new CardRepositoryImpl(), username);

        }
        this.playerRepository.add(player);
        return String.format(ConstantMessages.SUCCESSFULLY_ADDED_PLAYER, type, username);
    }
    @Override
    public String addCard(String type, String name) {
        Card card;
        if (type.equals("Trap")) {
            card = new TrapCard(name);
        } else {
            card = new MagicCard(name);
        }
        this.cardRepository.add(card);
        return String.format(ConstantMessages.SUCCESSFULLY_ADDED_CARD, type, name);
    }

    @Override
    public String addPlayerCard(String username, String cardName) {
        Player player = this.playerRepository.find(username);
        Card card = this.cardRepository.find(cardName);
        player.getCardRepository().add(card);

        return String.format(ConstantMessages.SUCCESSFULLY_ADDED_PLAYER_WITH_CARDS, cardName, username);
    }

    @Override
    public String fight(String attackUser, String enemyUser) {
        Player attacker = this.playerRepository.find(attackUser);
        Player enemy = this.playerRepository.find(enemyUser);

        battlefield.fight(attacker, enemy);

        return String.format("Attack user health %d - Enemy user health %d", attacker.getHealth(),
                enemy.getHealth());
    }

    @Override
    public String report() {

        StringBuilder sb = new StringBuilder();

        for (Player player : this.playerRepository.getPlayers()) {
            sb.append(player.toString())
                    .append(System.lineSeparator())
                    .append(ConstantMessages.DEFAULT_REPORT_SEPARATOR)
                    .append(System.lineSeparator());
        }
        
        return sb.toString();
    }
}
