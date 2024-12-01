// package com.example.demo.actor.effect;

// import com.example.demo.actor.ActiveActor;
// import com.example.demo.controller.Controller;

// public class AreaEffect extends ActiveActor {
//     private long creationTime;
//     private long duration;

//     public AreaEffect(Controller controller, double x, double y) {
//         super(controller);
//         setTranslateX(x);
//         setTranslateY(y);
//         this.creationTime = System.currentTimeMillis();
//         this.duration = 5000; // Effect lasts for 5 seconds
//         initializeGraphics("area_effect.png");
//     }

//     @Override
//     public void update() {
//         super.update();
//         if (System.currentTimeMillis() - creationTime > duration) {
//             setDestroyed(true);
//         }
//         // Handle collision with player
//     }
// }
