import { shallowMount } from '@vue/test-utils';
import { describe, it, expect, vi } from 'vitest';
import Hands from './Hands.vue';
import EnemyHand from './EnemyHand.vue';
import { Gesture, useState } from '../../state';
import MyHand from './MyHand.vue';

vi.mock('../../backend.ts', () => ({
    makeMove: () => {},
}));

describe('Players hands (Hands.vue)', () => {
    describe('when I made choise', () => {
        describe('show my hand', () => {
            it('- with choosen weapon', () => {
                const { getMyHandChoise } = setup({ myWeapon: 'PAPER' });
            });
        });
        describe('when opponent', () => {
            describe('has not yet choosen', () => {
                it('- show "PENDING" opponent hand', () => {
                    const { getEnemyHandChoise } = setup({
                        enemyWeapon: 'PENDING',
                    });
                    expect(getEnemyHandChoise()).toBe('PENDING');
                });
            });

            describe('has made choise', () => {
                it('- show chosen weapon', () => {
                    const { getEnemyHandChoise } = setup({
                        enemyWeapon: 'PAPER',
                    });
                    expect(getEnemyHandChoise()).toBe('PAPER');
                });
            });
        });
    });
});

type SetupProps = {
    myWeapon?: Gesture;
    enemyWeapon?: Gesture;
};
function setup({
    myWeapon = 'PENDING',
    enemyWeapon = 'PENDING',
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
