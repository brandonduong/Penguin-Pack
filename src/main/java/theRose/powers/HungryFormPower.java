package theRose.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import theRose.ModInitializer;
import theRose.cards.*;
import theRose.util.TextureLoader;

import java.util.concurrent.ThreadLocalRandom;

public class HungryFormPower extends AbstractPower implements CloneablePowerInterface {
    public AbstractCreature source;

    public static final String POWER_ID = ModInitializer.makeID("HungryFormPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    public static final String NAME = powerStrings.NAME;
    public static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    // We create 2 new textures *Using This Specific Texture Loader* - an 84x84 image and a 32x32 one.
    private static final Texture tex84 = TextureLoader.getTexture("theRoseResources/images/powers/placeholder_power84.png");
    private static final Texture tex32 = TextureLoader.getTexture("theRoseResources/images/powers/placeholder_power32.png");

    public HungryFormPower(final AbstractCreature owner, final AbstractCreature source, final int amount) {
        name = NAME;
        ID = POWER_ID;

        this.owner = owner;
        this.amount = amount;
        this.source = source;

        type = PowerType.BUFF;
        isTurnBased = false;

        // We load those textures here.
        this.region128 = new TextureAtlas.AtlasRegion(tex84, 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(tex32, 0, 0, 32, 32);

        updateDescription();
    }

    @Override
    public void atStartOfTurn() { // At the start of your turn
        // Create random Food cards
        AbstractCard card;
        AbstractCard[] foodCards = {new Donut(), new EnergyDrink(), new FriedChicken(), new Hamburger(), new Sushi()};
        int randomNum;

        // Consume food items
        for (int i = 0; i < this.amount; i++) {
            randomNum = ThreadLocalRandom.current().nextInt(0, 5); // Random num between [0, 4]
            card = foodCards[randomNum];

            AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(card, null, card.energyOnUse, true, true), true);
        }
    }

    @Override
    public void updateDescription() {
        if (this.amount == 1) {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
        }
        else {
            description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[2];
        }
    }

    @Override
    public AbstractPower makeCopy() {
        return new HungryFormPower(owner, source, amount);
    }
}
