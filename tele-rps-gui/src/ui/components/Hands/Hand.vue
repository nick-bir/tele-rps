<template>
    <div class="hand">
        <div class="hand__header">
            <Subheader>{{ name }}</Subheader>
        </div>
        <Weapon :type="props.choise" size="lg" outlined />
    </div>
</template>
<script setup lang="ts">
    import Subheader from '../Subheader.vue';
    import { defineProps } from 'vue';
    import { Figures } from '../../../state/state';
    import Weapon from '../Weapon.vue';

    const props = defineProps<{ choise: Figures; name: string }>();
</script>

<style scoped>
    .hand {
        display: inline-flex;
        flex-direction: column;
        align-items: center;
        width: 100px;
        font-size: 5rem;
        margin-bottom: 1rem;
        /* border: 1px solid silver; */
        border-radius: 10px;
        padding: 10px;
        justify-content: space-between;
    }

    .hand__header {
        flex-grow: 1;
    }

    .hand__header > * {
        margin: 10px;
    }
</style>
../../../state/api
