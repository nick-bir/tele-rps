import { shallowMount } from '@vue/test-utils';
import { describe, it, expect } from 'vitest';
import Hands from './Hands.vue';
import EnemyHand from './EnemyHand.vue';
import { Figures, useState } from '../../../state/state';
import MyHand from './MyHand.vue';

describe('Players hands (Hands.vue)', () => {
    describe('when I made choise', () => {
        describe('show my hand', () => {
            it('- with choosen weapon', () => {
                const { getMyHandChoise } = setup({ myWeapon: 'paper' });
            });
        });
        describe('when opponent', () => {
            describe('has not yet choosen', () => {
                it('- show "choosing" opponent hand', () => {
                    const { getEnemyHandChoise } = setup({
                        enemyWeapon: 'choosing',
                    });
                    expect(getEnemyHandChoise()).toBe('choosing');
                });
            });

            describe('has made choise', () => {
                it('- show chosen weapon', () => {
                    const { getEnemyHandChoise } = setup({
                        enemyWeapon: 'paper',
                    });
                    expect(getEnemyHandChoise()).toBe('paper');
                });
            });
        });
    });
});

type SetupProps = {
    myWeapon?: Figures;
    enemyWeapon?: Figures;
};
function setup({
    myWeapon = 'choosing',
    enemyWeapon = 'choosing',
}: SetupProps = {}) {
    const { setMyWeapon, setEnemyWeapon, startNewGame } = useState();

    startNewGame();
    setMyWeapon(myWeapon);
    setEnemyWeapon(enemyWeapon);

    const wrapper = shallowMount(Hands);

    const getMyHandChoise = () => wrapper.findComponent(MyHand).props('choise');
    const getEnemyHandChoise = () =>
        wrapper.findComponent(EnemyHand).props('choise');

    return {
        wrapper,
        getMyHandChoise,
        getEnemyHandChoise,
    };
}
