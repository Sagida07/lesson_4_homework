import java.util.Random;

public class Main {
    public static int bossHealth = 900;
    public static int bossDamage = 90;
    public static String bossDefence;
    public static int[] heroesHealth = {390, 400, 380, 300, 500, 310, 450, 490};
    public static int[] heroesDamage = {30, 35, 40, 0, 10, 15, 20, 25};
    public static String[] heroesAttackType = {"Strong guy", "Magician", "Kinetic", "Medic", "Golem", "Lucky", "Berserk", "Thor"};
    public static int roundNumber;

    public static void main(String[] args) {
        printStatistics();
        while (!isGameOver()) {
            playRound();
        }
    }

    public static void playRound() {
        roundNumber++;
        berserk();

        chooseBossDefence();
        medic();
        bossHits();

        golem();
        lucky();

        heroesHit();
        thor();
        printStatistics();
    }

    public static void chooseBossDefence() {
        Random random = new Random();
        int randomIndex = random.nextInt(heroesAttackType.length);
        bossDefence = heroesAttackType[randomIndex];
    }

    public static void bossHits() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                heroesHealth[i] -= bossDamage;
                if (heroesHealth[i] < 0) {
                    heroesHealth[i] = 0;
                }
                if (heroesHealth[6] <0){
                    heroesHealth[6] = 0;
                }
            }
        }
    }

    public static void heroesHit() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                int damage = heroesDamage[i];
                if (bossDefence == heroesAttackType[i]) {
                    Random random = new Random();
                    int coeff = random.nextInt(8) + 2;
                    damage = heroesDamage[i] * coeff;
                    System.out.println("Critical damage: " + damage);
                }
                bossHealth -= damage;
                if (bossHealth < 0) {
                    bossHealth = 0;
                }
            }
        }
    }

    public static void medic() {
        int healthPoint = 100;
        boolean justOneHealthInEachRound = false;

        for (int z = 0; z < heroesHealth.length; z++) {
            if (heroesHealth[3] > 0 && bossHealth >= 0) {
                if (heroesHealth[z] < 100 && heroesHealth[z] > 0) {
                    if (!justOneHealthInEachRound) {
                        if (heroesAttackType[z] != "Medic") {
                            justOneHealthInEachRound = true;
                            heroesHealth[z] += healthPoint;
                            System.out.println(heroesAttackType[3] + " just cured hero: " + heroesAttackType[z]);
                        }
                    }
                }
            }
        }
    }

    public static void golem() {
        int takesDamageFromBoss = 0;
        boolean onlyGetsOneHit = false;

        for (int x = 0; x < heroesHealth.length; x++) {
            if (heroesHealth[4] > 0 && bossHealth >= 0) {
                if (heroesAttackType[x] != "Golem") {
                    if (!onlyGetsOneHit) {
                        onlyGetsOneHit = true;
                        takesDamageFromBoss = bossDamage % heroesHealth[x];
                        heroesHealth[4] -= takesDamageFromBoss;
                        System.out.println(heroesAttackType[4] + " have just took boss damage " +
                                takesDamageFromBoss + " from hero " + heroesAttackType[x]);
                    }
                }
            }
        }
    }

    public static void lucky() {
        Random random = new Random();
        int chance = random.nextInt(10);
        if (chance < 1) {
            System.out.println(heroesAttackType[5] + " dodged the boss attack!");
        } else {
            heroesHealth[5] -= bossDamage;
            if (heroesHealth[5] < 0) {
                heroesHealth[5] = 0;
            }
            System.out.println(heroesAttackType[5] + " have just got hit by the boss");
        }
    }

    public static void thor() {
        Random random = new Random();
        boolean stun = random.nextBoolean();
        if (stun) {
            System.out.println(heroesAttackType[7] + " stunned the boss!");
            roundNumber++;
        }
    }

    public static void berserk() {
        int berserkIndex = 6;
        int blockDamage = bossDamage / 4;
        heroesHealth[berserkIndex] -= blockDamage;
        bossDamage += blockDamage;
        System.out.println(heroesAttackType[berserkIndex] + " blocked " + blockDamage +
                " boss damage and returned it");
    }


    public static boolean isGameOver() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }

        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("Boss won!!!");
        }
        return allHeroesDead;
    }

    public static void printStatistics() {
        System.out.println("ROUND " + roundNumber + " ------------");
        System.out.println("Boss health: " + bossHealth + " damage: " + bossDamage + " defence: " +
                (bossDefence == null ? "No defence" : bossDefence));
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesAttackType[i] + " health: " + heroesHealth[i] +
                    " damage: " + heroesDamage[i]);
        }
    }
}