import { shallowMount } from '@vue/test-utils';
import { describe, it, expect } from 'vitest';
import Game from './GamePage.vue';
import RpsSelector from '../components/RpsSelector.vue';
import Hands from '../components/Hands/Hands.vue';
import { Figures, useApi } from '../../logic/api';

describe('Game screen (Game.vue)', () => {
    // describe('always', () => {
    //     it('- show score', async () => {
    //         const { isScoreVisible } = setup();
    //         expect(isScoreVisible()).toBe(true);
    //     });
    // });
    describe('when new round started', () => {
        it('- hide players hands', () => {
            const { areHandsVisible } = setup();
            expect(areHandsVisible()).toBe(false);
        });
        it('- show rock/paper/scissors selector', () => {
            const { isRpsSelectorVisible } = setup();
            expect(isRpsSelectorVisible()).toBe(true);
        });

        describe('when I made choise', () => {
            it('- hide rock/paper/scissors selector', () => {
                const { isRpsSelectorVisible } = setup({
                    myWeapon: 'rock',
                });
                expect(isRpsSelectorVisible()).toBe(false);
            });
            it('- show players hands', async () => {
                const { selectWeapon, areHandsVisible } = setup({});
                await selectWeapon();
                expect(areHandsVisible()).toBe(true);
            });
        });
    });
    describe('when round finished', () => {
        it('- show players hands', () => {
            const { areHandsVisible } = setup({
                myWeapon: 'rock',
                enemyWeapon: 'paper',
            });
            expect(areHandsVisible()).toBe(true);
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
    const { startNewGame, setMyWeapon, setEnemyWeapon } = useApi();

    startNewGame();
    setMyWeapon(myWeapon);
    setEnemyWeapon(enemyWeapon);

    const wrapper = shallowMount(Game);

    const isRpsSelectorVisible = () =>
        wrapper.findComponent(RpsSelector).exists();
    const areHandsVisible = () => wrapper.findComponent(Hands).exists();

    const selectWeapon = () => {
        wrapper.findComponent(RpsSelector).vm.$emit('choise', 'rock');
    };

    return {
        wrapper,
        isRpsSelectorVisible,
        areHandsVisible,

        selectWeapon,
    };
}
