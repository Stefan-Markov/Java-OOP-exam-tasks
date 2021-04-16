package PlayersAndMonstersSecondSolution.core;


import PlayersAndMonstersSecondSolution.common.ConstantMessages;
import PlayersAndMonstersSecondSolution.core.interfaces.ManagerController;
import PlayersAndMonstersSecondSolution.models.battleFields.BattleFieldImpl;
import PlayersAndMonstersSecondSolution.models.battleFields.interfaces.Battlefield;
import PlayersAndMonstersSecondSolution.models.cards.interfaces.Card;
import PlayersAndMonstersSecondSolution.models.cards.MagicCard;
import PlayersAndMonstersSecondSolution.models.cards.TrapCard;
import PlayersAndMonstersSecondSolution.models.players.Advanced;
import PlayersAndMonstersSecondSolution.models.players.Beginner;
import PlayersAndMonstersSecondSolution.models.players.interfaces.Player;
import PlayersAndMonstersSecondSolution.repositories.interfaces.CardRepository;
import PlayersAndMonstersSecondSolution.repositories.CardRepositoryImpl;
import PlayersAndMonstersSecondSolution.repositories.interfaces.PlayerRepository;
import PlayersAndMonstersSecondSolution.repositories.PlayerRepositoryImpl;

public class ManagerControllerImpl implements ManagerController {
    private static final String MAGIC_CARD_TYPE = "Magic";
    private static final String TRAP_CARD_TYPE = "Trap";


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
        Player player = null;

        if (Beginner.class.getSimpleName().equals(type)) {
            player = new Beginner(new CardRepositoryImpl(), username);
        } else if (Advanced.class.getSimpleName().equals(type)) {
            player = new Advanced(new CardRepositoryImpl(), username);
        }
        this.playerRepository.add(player);
        String message = String.format(ConstantMessages.SUCCESSFULLY_ADDED_PLAYER, type, username);
        return message;
    }

    @Override
    public String addCard(String type, String name) {

        Card card = null;
        if (TRAP_CARD_TYPE.equals(type)) {
            card = new TrapCard(name);
        } else if (MAGIC_CARD_TYPE.equals(type)) {
            card = new MagicCard(name);
        }
        this.cardRepository.add(card);
        String message = String.format(ConstantMessages.SUCCESSFULLY_ADDED_CARD, type, name);
        return message;
    }

    @Override
    public String addPlayerCard(String username, String cardName) {
        Card card = this.cardRepository.find(cardName);
        Player player = this.playerRepository.find(username);

        player.getCardRepository().add(card);
        String message = String.format(ConstantMessages.SUCCESSFULLY_ADDED_PLAYER_WITH_CARDS, cardName, username);
        return message;
    }

    @Override
    public String fight(String attackUser, String enemyUser) {
        Player attackPlayer = this.playerRepository.find(attackUser);
        Player enemyPlayer = this.playerRepository.find(enemyUser);
        this.battlefield.fight(attackPlayer, enemyPlayer);
        String message = String.format(ConstantMessages.FIGHT_INFO, attackPlayer.getHealth(), enemyPlayer.getHealth());

        return message;
    }

    @Override
    public String report() {
        StringBuilder sb = new StringBuilder();

        for (Player player : this.playerRepository.getPlayers()) {
            sb.append(player.toString());
            sb.append(System.lineSeparator());
        }

        return sb.toString().trim();
    }
}
