<template>
    <div class="particle-container">
        <Particles id="tsparticles" :options="options" class="particle" />
        <Header class="on-up"></Header>

        <Home />

        <Footer />
    </div>
</template>

<script setup>
import Header from './components/Header.vue';
import Home from './views/Home.vue';
import Footer from './components/Footer.vue';
//配置particles
const options = {
    background: {
        color: {
            value: '#0d47a1',
        },
        // image: './image/2.jpg',
        // image: "url('~/image/2.jpg')",
        // position: "50% 50%",
        // repeat: "no-repeat",
        // size: "cover",
        // opacity: 1
    },
    fpsLimit: 120,
    interactivity: {
        events: {
            onClick: {
                enable: true,
                mode: 'push',
            },
            onHover: {
                enable: true,
                mode: 'repulse',
            },
            resize: true,
        },
        modes: {
            bubble: {
                distance: 400,
                duration: 2,
                opacity: 0.8,
                size: 40,
            },
            push: {
                quantity: 4,
            },
            repulse: {
                distance: 200,
                duration: 0.4,
            },
        },
    },
    particles: {
        color: {
            value: '#ffffff',
        },
        links: {
            color: '#ffffff',
            distance: 150,
            enable: true,
            opacity: 0.5,
            width: 1,
        },
        move: {
            direction: 'none',
            enable: true,
            outMode: 'bounce',
            random: false,
            speed: 6,
            straight: false,
        },
        number: {
            density: {
                enable: true,
                area: 800,
            },
            value: 80,
        },
        opacity: {
            value: 0.5,
        },
        shape: {
            type: 'circle',
        },
        size: {
            random: true,
            value: 5,
        },
    },
    detectRetina: true,
}

</script>

<style scoped>

.particle-container {
    position: relative;
    z-index: 0;
    /* 容器的z-index设置为0 */
}

.particle {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    z-index: -1;
    /* 粒子背景在容器后面 */
}

.on-up {
    position: fixed;
    top: 0;
    z-index: 1;
}
</style>