package theRose.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import theRose.ModInitializer;
import theRose.characters.TheRose;

import static theRose.ModInitializer.makeCardPath;

public class BellyBump extends AbstractDynamicCard {

    /*
     * Belly Bump: Deal damage equal to the number of Food items eaten this combat. Quarter(Halve) the counter.
     */

    // TEXT DECLARATION

    public static final String ID = ModInitializer.makeID(BellyBump.class.getSimpleName());
    public static final String IMG = makeCardPath("Attack.png");

    // /TEXT DECLARATION/

    // Upgraded description
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = TheRose.Enums.COLOR_GRAY;

    private static final int COST = 1;
    private static final int DAMAGE = 0;
    private static final int FACTOR = 4; // Divide by 4
    private static final int UPGRADE_FACTOR = -2; // Divide by 2

    // /STAT DECLARATION/

    public BellyBump() {
        super(ID, IMG, COST, TYPE, COLOR, RARITY, TARGET);
        baseDamage = DAMAGE;
        baseMagicNumber = FACTOR;
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        // If no stacks, do nothing
        if (!AbstractDungeon.player.hasPower("theRose:FoodEatenPower")) {
            return;
        }

        // Deal damage
        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(m, new DamageInfo(p, AbstractDungeon.player.getPower("theRose:FoodEatenPower").amount, damageTypeForTurn),
                        AbstractGameAction.AttackEffect.SMASH));

        // Reduce food eaten counter
        int reduce = p.getPower("theRose:FoodEatenPower").amount / baseMagicNumber;

        if (baseMagicNumber == 4) {
            reduce *= 3;
        }

        AbstractDungeon.actionManager.addToBottom(new ReducePowerAction(p, p, "theRose:FoodEatenPower", reduce));
    }


    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_FACTOR);
            rawDescription = UPGRADE_DESCRIPTION;
            initializeDescription();
        }
    }
}