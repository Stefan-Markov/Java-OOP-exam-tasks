package PlayersAndMonsters.repositories;

import PlayersAndMonsters.models.cards.interfaces.Card;
import PlayersAndMonsters.repositories.interfaces.CardRepository;

import java.util.ArrayList;
import java.util.List;

public class CardRepositoryImpl implements CardRepository {

    private List<Card> cards;

    public CardRepositoryImpl() {
        this.cards = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return this.cards.size();
    }

    @Override
    public List<Card> getCards() {
        return this.cards;
    }

    @Override
    public void add(Card card) {
        if (card == null) {
            throw new IllegalArgumentException("Card cannot be null!");
        }
        boolean contains = this.cards.stream().anyMatch(p -> p.getName().equals(card.getName()));
        if (contains) {
            throw new IllegalArgumentException(String.format("Card %s already exists!", card.getName()));
        }
        this.cards.add(card);
    }

    @Override
    public boolean remove(Card card) {
        if (card == null) {
            throw new IllegalArgumentException("Card cannot be null!");
        }
        return this.cards.remove(card);
    }

    @Override
    public Card find(String name) {
        return this.cards
                .stream()
                .filter(f -> f.getName().equals(name))
                .findFirst()
                .orElse(null);
    }
}
