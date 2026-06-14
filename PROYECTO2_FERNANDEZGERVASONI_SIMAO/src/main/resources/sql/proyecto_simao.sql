-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 14-06-2026 a las 13:29:03
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `proyecto_simao`
--
CREATE DATABASE IF NOT EXISTS `proyecto_simao` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `proyecto_simao`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `alumno`
--

CREATE TABLE `alumno` (
  `mayoria_edad` bit(1) NOT NULL,
  `id` bigint(20) NOT NULL,
  `id_curso` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `alumno`
--

INSERT INTO `alumno` (`mayoria_edad`, `id`, `id_curso`) VALUES
(b'1', 9, '2VIFC302'),
(b'0', 10, '2VIFC302'),
(b'1', 11, '1VIFC302'),
(b'1', 12, '2VIFC303'),
(b'1', 13, '1IFC303'),
(b'1', 14, '1@IFC303'),
(b'0', 15, '1VIFC302'),
(b'0', 16, '1VIFC303'),
(b'1', 17, '2VIFC303'),
(b'1', 18, '2VIFC302'),
(b'1', 19, '2@IFC303'),
(b'1', 20, '2IFC303');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ciclo`
--

CREATE TABLE `ciclo` (
  `id` bigint(20) NOT NULL,
  `tipo` enum('DAM','DAW_DIURNO','DAW_VESPERTINO','DAW_VIRTUAL') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `ciclo`
--

INSERT INTO `ciclo` (`id`, `tipo`) VALUES
(1, 'DAW_DIURNO'),
(2, 'DAW_VESPERTINO'),
(3, 'DAW_VIRTUAL'),
(4, 'DAM');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `curso`
--

CREATE TABLE `curso` (
  `codigo` varchar(255) NOT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `ciclo_id` bigint(20) DEFAULT NULL,
  `coordinador_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `curso`
--

INSERT INTO `curso` (`codigo`, `nombre`, `ciclo_id`, `coordinador_id`) VALUES
('1@IFC303', '1º DAW Virtual', 3, 2),
('1IFC303', '1º DAW Diurno', 1, 2),
('1VIFC302', '1º DAM', 4, 3),
('1VIFC303', '1º DAW Vespertino', 2, 4),
('2@IFC303', '2º DAW Virtual', 3, 1),
('2IFC303', '2º DAW Diurno', 1, 1),
('2VIFC302', '2º DAM', 4, 3),
('2VIFC303', '2º DAW Vespertino', 2, 4);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `empresa`
--

CREATE TABLE `empresa` (
  `id` bigint(20) NOT NULL,
  `direccion` varchar(255) DEFAULT NULL,
  `nombre` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `empresa`
--

INSERT INTO `empresa` (`id`, `direccion`, `nombre`) VALUES
(1, 'Parque Tecnológico de Gijón', 'SoftAstur'),
(2, 'Calle Uría 24, Oviedo', 'IndraTech'),
(3, 'Av. Constitución 12, Avilés', 'Global Labs'),
(4, 'Polígono Silvota, Llanera', 'CyberNorth');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `evaluacion_ra`
--

CREATE TABLE `evaluacion_ra` (
  `id` bigint(20) NOT NULL,
  `estado` enum('NO_APLICA','NO_SUPERADO','PENDIENTE','SUPERADO') DEFAULT NULL,
  `formacion_id` bigint(20) DEFAULT NULL,
  `resultado_aprendizaje_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `evaluacion_ra`
--

INSERT INTO `evaluacion_ra` (`id`, `estado`, `formacion_id`, `resultado_aprendizaje_id`) VALUES
(1, 'PENDIENTE', 1, 3),
(2, 'PENDIENTE', 1, 4),
(3, 'PENDIENTE', 2, 3),
(4, 'PENDIENTE', 2, 4),
(5, 'PENDIENTE', 3, 1),
(6, 'PENDIENTE', 3, 2),
(7, 'PENDIENTE', 3, 6),
(8, 'PENDIENTE', 3, 7),
(9, 'PENDIENTE', 4, 5),
(10, 'SUPERADO', 5, 3),
(11, 'NO_APLICA', 5, 4);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `falta`
--

CREATE TABLE `falta` (
  `id` bigint(20) NOT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `fecha` date DEFAULT NULL,
  `justificada` bit(1) NOT NULL,
  `justificante` varchar(255) DEFAULT NULL,
  `formacion_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `falta`
--

INSERT INTO `falta` (`id`, `descripcion`, `fecha`, `justificada`, `justificante`, `formacion_id`) VALUES
(1, 'Ausencia por consulta médica', '2026-06-13', b'0', NULL, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `formacion_empresa`
--

CREATE TABLE `formacion_empresa` (
  `id` bigint(20) NOT NULL,
  `estado` tinyint(4) DEFAULT NULL,
  `fecha_fin` date DEFAULT NULL,
  `fecha_ini` date DEFAULT NULL,
  `periodo` tinyint(4) DEFAULT NULL,
  `alumno_id` bigint(20) DEFAULT NULL,
  `empresa_id` bigint(20) DEFAULT NULL,
  `tutor_centro_id` bigint(20) DEFAULT NULL,
  `tutor_empresa_id` bigint(20) DEFAULT NULL,
  `valoracion` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `formacion_empresa`
--

INSERT INTO `formacion_empresa` (`id`, `estado`, `fecha_fin`, `fecha_ini`, `periodo`, `alumno_id`, `empresa_id`, `tutor_centro_id`, `tutor_empresa_id`, `valoracion`) VALUES
(1, 2, '2026-05-31', '2026-05-01', 0, 9, 2, 4, 6, NULL),
(2, 1, '2026-07-15', '2026-06-15', 1, 10, 1, 1, 5, NULL),
(3, 2, '2026-06-30', '2026-06-01', 0, 12, 3, 2, 7, NULL),
(4, 2, '2026-05-31', '2026-04-01', 0, 11, 4, 3, 8, NULL),
(5, 3, '2026-06-30', '2026-06-01', 1, 18, 2, 3, 6, NULL),
(6, 2, '2026-06-30', '2026-06-01', 1, 19, 1, 3, 5, NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `modulo`
--

CREATE TABLE `modulo` (
  `id` bigint(20) NOT NULL,
  `nombre` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `modulo`
--

INSERT INTO `modulo` (`id`, `nombre`) VALUES
(1, 'Desarrollo Web en Entorno Cliente'),
(2, 'Desarrollo Web en Entorno Servidor'),
(3, 'Empresa e Iniciativa Emprendedora'),
(4, 'Despliegue de Aplicaciones Web');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `modulo_curso`
--

CREATE TABLE `modulo_curso` (
  `id` bigint(20) NOT NULL,
  `curso_codigo` varchar(255) DEFAULT NULL,
  `modulo_id` bigint(20) DEFAULT NULL,
  `profesor_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `modulo_curso`
--

INSERT INTO `modulo_curso` (`id`, `curso_codigo`, `modulo_id`, `profesor_id`) VALUES
(1, '2VIFC303', 1, 2),
(2, '2VIFC302', 2, 3),
(3, '1VIFC302', 3, 4),
(4, '2VIFC303', 4, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `persona`
--

CREATE TABLE `persona` (
  `id` bigint(20) NOT NULL,
  `email` varchar(255) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `perfil` enum('ADMIN','ALUMNADO','PROFESORADO','TUTOR') NOT NULL,
  `user` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `persona`
--

INSERT INTO `persona` (`id`, `email`, `nombre`, `password`, `perfil`, `user`) VALUES
(1, 'jperez@mail.es', 'Juan Pérez', '1a1dc91c907325c69271ddf0c944bc72', 'PROFESORADO', 'jperez'),
(2, 'mgarcia@mail.es', 'María García', '1a1dc91c907325c69271ddf0c944bc72', 'PROFESORADO', 'mgarcia'),
(3, 'lrodriguez@mail.es', 'Luis Rodríguez', '1a1dc91c907325c69271ddf0c944bc72', 'PROFESORADO', 'lrodriguez'),
(4, 'asantos@mail.es', 'Ana Santos', '1a1dc91c907325c69271ddf0c944bc72', 'PROFESORADO', 'asantos'),
(5, 'rfernandez@softastur.es', 'Rubén Fernández', '1a1dc91c907325c69271ddf0c944bc72', 'TUTOR', 'rfernandez'),
(6, 'cvega@indratech.es', 'Carlos Vega', '1a1dc91c907325c69271ddf0c944bc72', 'TUTOR', 'cvega'),
(7, 'mprieto@globallabs.es', 'Marta Prieto', '1a1dc91c907325c69271ddf0c944bc72', 'TUTOR', 'mprieto'),
(8, 'jarias@cybernorth.es', 'Javier Arias', 'b9b57aae83585e17ede4570dcede353c', 'TUTOR', 'jarias'),
(9, 'alberto@mail.es', 'Alberto Díaz', '1a1dc91c907325c69271ddf0c944bc72', 'ALUMNADO', 'adiaz'),
(10, 'laura@mail.es', 'Laura Fernández', '1a1dc91c907325c69271ddf0c944bc72', 'ALUMNADO', 'lfernandez'),
(11, 'sergio@mail.es', 'Sergio Álvarez', '1a1dc91c907325c69271ddf0c944bc72', 'ALUMNADO', 'salvarez'),
(12, 'lucia@mail.es', 'Lucía Martínez', '1a1dc91c907325c69271ddf0c944bc72', 'ALUMNADO', 'lmartinez'),
(13, 'daniel@mail.es', 'Daniel García', '1a1dc91c907325c69271ddf0c944bc72', 'ALUMNADO', 'dgarcia'),
(14, 'elena@mail.es', 'Elena Suárez', '1a1dc91c907325c69271ddf0c944bc72', 'ALUMNADO', 'esaurez'),
(15, 'pablo@mail.es', 'Pablo Iglesias', '1a1dc91c907325c69271ddf0c944bc72', 'ALUMNADO', 'piglesias'),
(16, 'sara@mail.es', 'Sara López', '1a1dc91c907325c69271ddf0c944bc72', 'ALUMNADO', 'slopez'),
(17, 'ivan@mail.es', 'Iván Castro', '1a1dc91c907325c69271ddf0c944bc72', 'ALUMNADO', 'icastro'),
(18, 'nuria@mail.es', 'Nuria Blanco', '1a1dc91c907325c69271ddf0c944bc72', 'ALUMNADO', 'nblanco'),
(19, 'jorge@mail.es', 'Jorge Ruiz', '1a1dc91c907325c69271ddf0c944bc72', 'ALUMNADO', 'jruiz'),
(20, 'clara@mail.es', 'Clara Alonso', '1a1dc91c907325c69271ddf0c944bc72', 'ALUMNADO', 'calonso');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `profesor`
--

CREATE TABLE `profesor` (
  `id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `profesor`
--

INSERT INTO `profesor` (`id`) VALUES
(1),
(2),
(3),
(4);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `resultado_aprendizaje`
--

CREATE TABLE `resultado_aprendizaje` (
  `id` bigint(20) NOT NULL,
  `codigo` varchar(255) DEFAULT NULL,
  `descripcion` varchar(255) DEFAULT NULL,
  `modulo_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `resultado_aprendizaje`
--

INSERT INTO `resultado_aprendizaje` (`id`, `codigo`, `descripcion`, `modulo_id`) VALUES
(1, 'RA1 - DWEC', 'Desarrolla interfaces web dinámicas', 1),
(2, 'RA2 - DWEC', 'Gestiona eventos y DOM', 1),
(3, 'RA1 - DWES', 'Implementa aplicaciones servidor', 2),
(4, 'RA2 - DWES', 'Accede a bases de datos', 2),
(5, 'RA1 - EIE', 'Analiza modelos de negocio', 3),
(6, 'RA1 - DespAW', 'Publica aplicaciones web', 4),
(7, 'RA2 - DespAW', 'Configura servidores', 4);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tutor`
--

CREATE TABLE `tutor` (
  `id` bigint(20) NOT NULL,
  `empresa_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `tutor`
--

INSERT INTO `tutor` (`id`, `empresa_id`) VALUES
(5, 1),
(6, 2),
(7, 3),
(8, 4);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `alumno`
--
ALTER TABLE `alumno`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKai7w6ro86sq58nbm0qf3gyckf` (`id_curso`);

--
-- Indices de la tabla `ciclo`
--
ALTER TABLE `ciclo`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `curso`
--
ALTER TABLE `curso`
  ADD PRIMARY KEY (`codigo`),
  ADD KEY `FKl9ynano44mvcsw2tthbvgpw88` (`ciclo_id`),
  ADD KEY `FK9slqud5ygfs99s9dv1v4iiohf` (`coordinador_id`);

--
-- Indices de la tabla `empresa`
--
ALTER TABLE `empresa`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UK2fqlxbcs4h827hio1qam0dhd3` (`nombre`);

--
-- Indices de la tabla `evaluacion_ra`
--
ALTER TABLE `evaluacion_ra`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKpfuxy5sbwg6ojs87ggxhcd4mq` (`formacion_id`),
  ADD KEY `FKbhwb9y32yv4hstxt1myjxit50` (`resultado_aprendizaje_id`);

--
-- Indices de la tabla `falta`
--
ALTER TABLE `falta`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK1n31btwn3m4ynw9c8nwj3wte9` (`formacion_id`);

--
-- Indices de la tabla `formacion_empresa`
--
ALTER TABLE `formacion_empresa`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK4gcs2or1gwlwghfs3usk8p7aj` (`alumno_id`),
  ADD KEY `FKkg87u9i7xyof1ytqo88kxq3n1` (`empresa_id`),
  ADD KEY `FKsrjjxvg6r0j2ibmqc8nlpuoti` (`tutor_centro_id`),
  ADD KEY `FKmgpqxlbhgyx1otuuosry6hh5d` (`tutor_empresa_id`);

--
-- Indices de la tabla `modulo`
--
ALTER TABLE `modulo`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `modulo_curso`
--
ALTER TABLE `modulo_curso`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKlttb9hboq1pkkabnk9ii2f30q` (`curso_codigo`),
  ADD KEY `FKi10wgid0wb0edmb1w1w4jh6oo` (`modulo_id`),
  ADD KEY `FKrybtflwkqjolwcg7qow9ietrg` (`profesor_id`);

--
-- Indices de la tabla `persona`
--
ALTER TABLE `persona`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `UKbfxfxg15pmy0c1imvi6ucoeem` (`email`),
  ADD UNIQUE KEY `UKt0s7a8ki20akhk633wory5avp` (`user`);

--
-- Indices de la tabla `profesor`
--
ALTER TABLE `profesor`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `resultado_aprendizaje`
--
ALTER TABLE `resultado_aprendizaje`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKk7n2w3u4j5ipkewk180m0417` (`modulo_id`);

--
-- Indices de la tabla `tutor`
--
ALTER TABLE `tutor`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKqbqmwm7h7295s2ik70ungcjv7` (`empresa_id`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `ciclo`
--
ALTER TABLE `ciclo`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `empresa`
--
ALTER TABLE `empresa`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `evaluacion_ra`
--
ALTER TABLE `evaluacion_ra`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT de la tabla `falta`
--
ALTER TABLE `falta`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `formacion_empresa`
--
ALTER TABLE `formacion_empresa`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT de la tabla `modulo`
--
ALTER TABLE `modulo`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `modulo_curso`
--
ALTER TABLE `modulo_curso`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `persona`
--
ALTER TABLE `persona`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT de la tabla `resultado_aprendizaje`
--
ALTER TABLE `resultado_aprendizaje`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `alumno`
--
ALTER TABLE `alumno`
  ADD CONSTRAINT `FKai7w6ro86sq58nbm0qf3gyckf` FOREIGN KEY (`id_curso`) REFERENCES `curso` (`codigo`),
  ADD CONSTRAINT `FKo9irxnoeipyk0bbu66g8vqpn5` FOREIGN KEY (`id`) REFERENCES `persona` (`id`);

--
-- Filtros para la tabla `curso`
--
ALTER TABLE `curso`
  ADD CONSTRAINT `FK9slqud5ygfs99s9dv1v4iiohf` FOREIGN KEY (`coordinador_id`) REFERENCES `profesor` (`id`),
  ADD CONSTRAINT `FKl9ynano44mvcsw2tthbvgpw88` FOREIGN KEY (`ciclo_id`) REFERENCES `ciclo` (`id`);

--
-- Filtros para la tabla `evaluacion_ra`
--
ALTER TABLE `evaluacion_ra`
  ADD CONSTRAINT `FKbhwb9y32yv4hstxt1myjxit50` FOREIGN KEY (`resultado_aprendizaje_id`) REFERENCES `resultado_aprendizaje` (`id`),
  ADD CONSTRAINT `FKpfuxy5sbwg6ojs87ggxhcd4mq` FOREIGN KEY (`formacion_id`) REFERENCES `formacion_empresa` (`id`);

--
-- Filtros para la tabla `falta`
--
ALTER TABLE `falta`
  ADD CONSTRAINT `FK1n31btwn3m4ynw9c8nwj3wte9` FOREIGN KEY (`formacion_id`) REFERENCES `formacion_empresa` (`id`);

--
-- Filtros para la tabla `formacion_empresa`
--
ALTER TABLE `formacion_empresa`
  ADD CONSTRAINT `FK4gcs2or1gwlwghfs3usk8p7aj` FOREIGN KEY (`alumno_id`) REFERENCES `alumno` (`id`),
  ADD CONSTRAINT `FKkg87u9i7xyof1ytqo88kxq3n1` FOREIGN KEY (`empresa_id`) REFERENCES `empresa` (`id`),
  ADD CONSTRAINT `FKmgpqxlbhgyx1otuuosry6hh5d` FOREIGN KEY (`tutor_empresa_id`) REFERENCES `tutor` (`id`),
  ADD CONSTRAINT `FKsrjjxvg6r0j2ibmqc8nlpuoti` FOREIGN KEY (`tutor_centro_id`) REFERENCES `profesor` (`id`);

--
-- Filtros para la tabla `modulo_curso`
--
ALTER TABLE `modulo_curso`
  ADD CONSTRAINT `FKi10wgid0wb0edmb1w1w4jh6oo` FOREIGN KEY (`modulo_id`) REFERENCES `modulo` (`id`),
  ADD CONSTRAINT `FKlttb9hboq1pkkabnk9ii2f30q` FOREIGN KEY (`curso_codigo`) REFERENCES `curso` (`codigo`),
  ADD CONSTRAINT `FKrybtflwkqjolwcg7qow9ietrg` FOREIGN KEY (`profesor_id`) REFERENCES `profesor` (`id`);

--
-- Filtros para la tabla `profesor`
--
ALTER TABLE `profesor`
  ADD CONSTRAINT `FK3s5vdutxb00ftmsqftw0vof5m` FOREIGN KEY (`id`) REFERENCES `persona` (`id`);

--
-- Filtros para la tabla `resultado_aprendizaje`
--
ALTER TABLE `resultado_aprendizaje`
  ADD CONSTRAINT `FKk7n2w3u4j5ipkewk180m0417` FOREIGN KEY (`modulo_id`) REFERENCES `modulo` (`id`);

--
-- Filtros para la tabla `tutor`
--
ALTER TABLE `tutor`
  ADD CONSTRAINT `FKfqqku9bu6vpvdihqk05s45hut` FOREIGN KEY (`id`) REFERENCES `persona` (`id`),
  ADD CONSTRAINT `FKqbqmwm7h7295s2ik70ungcjv7` FOREIGN KEY (`empresa_id`) REFERENCES `empresa` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
