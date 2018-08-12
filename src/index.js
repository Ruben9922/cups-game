import * as THREE from 'three';

var scene = new THREE.Scene();

var camera = new THREE.PerspectiveCamera(60, window.innerWidth / window.innerHeight, 0.1, 1000);
camera.position.set(0, 20, 90);
camera.lookAt(0, 0, 0);

var renderer = new THREE.WebGLRenderer();
renderer.setSize(window.innerWidth, window.innerHeight);
document.body.appendChild(renderer.domElement);

var geometry = createCupGeometry();
var material = new THREE.MeshLambertMaterial({color: 0xfd59d7});
var cup = new THREE.Mesh(geometry, material);
cup.rotateOnAxis(new THREE.Vector3(1, 0, 0), Math.PI / 2);
scene.add(cup);

let cup1 = cup.clone();
cup1.translateX(-40);
scene.add(cup1);

let cup2 = cup.clone();
cup2.translateX(40);
scene.add(cup2);

var light = new THREE.PointLight(0xFFFF00);
light.position.set(10, 0, 25);
scene.add(light);

var render = function () {
  requestAnimationFrame(render);
  // cup.geometry.computeVertexNormals();
  renderer.render(scene, camera);
};

render();

function createCupGeometry() {
  let sides = 50;
  let height = 30;
  let topRadius = 10;
  let bottomRadius = 15;

  // Compute vertex positions of unit circle
  let centralAngle = 2 * Math.PI / sides;
  let unitCircleVertices = [];

  for (let i = 0; i < sides; i++) {
    let vertex = new THREE.Vector3(
      Math.cos(centralAngle * i),
      Math.sin(centralAngle * i),
      0
    );
    unitCircleVertices.push(vertex);
  }

  // Hence compute the vertex positions of top and bottom of cup
  let topVertices = unitCircleVertices.map(v => v.clone().multiplyScalar(topRadius).add(new THREE.Vector3(0, 0, -height / 2)));
  let bottomVertices = unitCircleVertices.map(v => v.clone().multiplyScalar(bottomRadius).add(new THREE.Vector3(0, 0, height / 2)));

  // Add vertices to geometry
  let geometry = new THREE.Geometry();
  for (let i = 0; i < sides; i++) {

    let top = topVertices[i];
    let bottom = bottomVertices[i];

    geometry.vertices.push(top, bottom);
  }
  geometry.vertices.push(new THREE.Vector3(0, 0, -height / 2));

  // Create and add faces to geometry
  for (let i = 0; i < sides; i++) {
    let topPrevIndex = (i * 2) % (sides * 2);
    let bottomPrevIndex = ((i * 2) + 1) % (sides * 2);
    let topIndex = ((i * 2) + 2) % (sides * 2);
    let bottomIndex = ((i * 2) + 3) % (sides * 2);

    // Faces for side
    geometry.faces.push(
      new THREE.Face3(topPrevIndex, bottomIndex, bottomPrevIndex),
      new THREE.Face3(bottomIndex, topPrevIndex, topIndex)
    );

    // Face for top
    geometry.faces.push(
      new THREE.Face3(geometry.vertices.length - 1, topIndex, topPrevIndex)
    );

    // geometry.computeBoundingSphere();
    // geometry.computeFaceNormals();
    geometry.computeVertexNormals();
    geometry.verticesNeedUpdate = true;
    geometry.elementsNeedUpdate = true;
  }

  return geometry;
}
