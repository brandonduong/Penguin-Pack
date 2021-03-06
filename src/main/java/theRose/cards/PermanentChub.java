package theRose.cards;

import basemod.abstracts.CustomSavable;
import basemod.helpers.BaseModCardTags;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRose.ModInitializer;
import theRose.characters.TheRose;

import java.util.Iterator;

import static theRose.ModInitializer.foodEaten;
import static theRose.ModInitializer.makeCardPath;

public class PermanentChub extends AbstractDynamicCard {

    /*
     * Permanent Chub: Gain Block equal to the number of Food items eaten this game. Exhaust. (!B)
     */


    // TEXT DECLARATION

    public static final String ID = ModInitializer.makeID(PermanentChub.class.getSimpleName());
    public static final String IMG = makeCardPath("Skill.png");

    // /TEXT DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = TheRose.Enums.COLOR_GRAY;

    private static final int COST = 2;
    private static final int UPGRADE_COST = 1;

    // /STAT DECLARATION/


    public PermanentChub() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        this.exhaust = true;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int counter = 0;
        if (p.hasPower("theRose:FoodEatenPower")) {
            counter = p.getPower("theRose:FoodEatenPower").amount;
        }

        this.addToBot(new GainBlockAction(p, p, foodEaten + counter));
    }

    //Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBaseCost(UPGRADE_COST);
            initializeDescription();
        }
    }

}