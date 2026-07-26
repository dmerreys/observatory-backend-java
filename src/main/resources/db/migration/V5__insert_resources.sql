-- Seed de recursos reales sobre IA en el contexto ecuatoriano.
-- Un video featured = true por topic (ETHICS, GOVERNANCE, GENERAL) para que
-- se muestren las miniaturas de YouTube en el spotlight del home.

-- ============================================================
-- ETHICS
-- ============================================================

INSERT INTO resources (title, description, type, url, source, topic, featured, created_at, updated_at)
VALUES
    (
        'Un hito para el futuro digital del Ecuador: ética de la IA y estrategia nacional',
        'Mensaje institucional de Tatiana Villegas, Directora de la UNESCO en Ecuador, con motivo de la presentación de la RAM, que contribuye a la implementación de la Recomendación de la UNESCO sobre la Ética de la Inteligencia Artificial y de la Estrategia Nacional del Ecuador impulsada por el MINTEL.',
        'video',
        'https://www.youtube.com/embed/NIyjMZauOGc',
        'UNESCO Ecuador',
        'ETHICS',
        true,
        NOW() - INTERVAL '3 days',
        NULL
    ),
    (
        'Ética de la inteligencia artificial en América Latina',
        'Documento de referencia académica sobre los principios éticos aplicados al desarrollo y uso de la IA en el contexto latinoamericano.',
        'link',
        'https://ctslab.org/etica-ia-america-latina',
        'CTS-Lab Ecuador',
        'ETHICS',
        false,
        NOW() - INTERVAL '12 days',
        NULL
    ),
    (
        'La ética en la IA y la integridad académica: perspectiva de estudiantes en Guayaquil',
        'Estudio mixto aplicado a 380 estudiantes universitarios de Guayaquil sobre su comprensión de los dilemas éticos del uso de la IA y su relación con la integridad académica.',
        'pdf',
        'https://www.researchgate.net/publication/385101545_La_etica_en_la_Inteligencia_Artificial_y_la_integridad_academica_perspectiva_de_los_estudiantes_universitarios_en_Guayaquil',
        'ResearchGate',
        'ETHICS',
        false,
        NOW() - INTERVAL '20 days',
        NULL
    ),
    (
        'Uso y percepción de la IA en estudiantes de bachillerato ecuatoriano',
        'Análisis de las implicaciones pedagógicas, evaluativas y éticas del uso de herramientas de IA por estudiantes de tercero de bachillerato en Ecuador.',
        'pdf',
        'https://www.researchgate.net/publication/402061766_Uso_y_percepcion_de_la_inteligencia_artificial_en_estudiantes_de_bachillerato_ecuatoriano',
        'ResearchGate',
        'ETHICS',
        false,
        NOW() - INTERVAL '25 days',
        NULL
    ),
    (
        'Artificial intelligence and academic integrity: exploring plagiarism in Ecuadorian universities',
        'Estudio internacional sobre la detección de plagio generado por IA en universidades ecuatorianas, publicado en una revista especializada en integridad educativa.',
        'link',
        'https://link.springer.com/article/10.1007/s40979-025-00209-3',
        'International Journal for Educational Integrity',
        'ETHICS',
        false,
        NOW() - INTERVAL '30 days',
        NULL
    ),
    (
        'El uso responsable y ético de la IA en la educación superior ecuatoriana',
        'Identifica brechas en infraestructura digital, capacitación docente y sensibilización sobre riesgos como sesgos algorítmicos, privacidad de datos y plagio académico.',
        'link',
        'https://polodelconocimiento.com/ojs/index.php/es/article/view/9724',
        'Polo del Conocimiento',
        'ETHICS',
        false,
        NOW() - INTERVAL '40 days',
        NULL
    );

-- ============================================================
-- GOVERNANCE
-- ============================================================

INSERT INTO resources (title, description, type, url, source, topic, featured, created_at, updated_at)
VALUES
    (
        'Ecuador fortalece su gobernanza de la Inteligencia Artificial',
        'Ecuador se convierte en el tercer país de América Latina en desarrollar la Readiness Assessment Methodology (RAM), herramienta de la UNESCO para evaluar la preparación de los países en la implementación ética y responsable de la IA.',
        'video',
        'https://www.youtube.com/embed/LxXKTQDWP7s',
        'UNESCO',
        'GOVERNANCE',
        true,
        NOW() - INTERVAL '5 days',
        NULL
    ),
    (
        'Estrategia EFIA-EC: fomento del desarrollo y uso ético y responsable de la IA',
        'Instrumento nacional del MINTEL que define la hoja de ruta 2025-2029 para la adopción responsable de la inteligencia artificial en Ecuador.',
        'link',
        'https://www.telecomunicaciones.gob.ec/gobierno-de-daniel-noboa-presenta-la-estrategia-para-la-inteligencia-artificial-en-ecuador/',
        'MINTEL',
        'GOVERNANCE',
        false,
        NOW() - INTERVAL '15 days',
        NULL
    ),
    (
        'Norma General para la Protección de Datos Personales en el Uso de Sistemas de IA',
        'Resolución SPDP-SPD-2026-0009-R que establece principios, obligaciones y mecanismos de control aplicables al tratamiento de datos personales mediante sistemas de inteligencia artificial.',
        'link',
        'https://spdp.gob.ec/resoluciones2/',
        'SPDP',
        'GOVERNANCE',
        false,
        NOW() - INTERVAL '18 days',
        NULL
    ),
    (
        'Análisis jurídico de la EFIA-EC en el ordenamiento ecuatoriano',
        'Revisión de la naturaleza jurídica de la Estrategia EFIA-EC como instrumento de soft law, sus alcances y los vacíos normativos pendientes.',
        'link',
        'https://derechoecuador.com/la-inteligencia-artificial-en-el-ecuador/',
        'Derecho Ecuador',
        'GOVERNANCE',
        false,
        NOW() - INTERVAL '22 days',
        NULL
    ),
    (
        'Publicación oficial de la EFIA-EC en el Registro Oficial',
        'Detalle del Acuerdo Ministerial MINTEL-MINTEL-2025-0030 y el proceso de construcción de la estrategia, que involucró a más de 40 entidades públicas, académicas y de cooperación internacional.',
        'link',
        'https://www.lexis.com.ec/noticias/registro-oficial-del-dia-ecuador-aprueba-estrategia-nacional-para-el-desarrollo-y-uso-etico-de-la-inteligencia-artificial',
        'Registro Oficial / Lexis',
        'GOVERNANCE',
        false,
        NOW() - INTERVAL '28 days',
        NULL
    );

-- ============================================================
-- GENERAL
-- ============================================================

INSERT INTO resources (title, description, type, url, source, topic, featured, created_at, updated_at)
VALUES
    (
        '¿Reemplazo o evolución? El verdadero impacto de la IA en las empresas ecuatorianas',
        'Ximena Aulestia entrevista a Francisco Ordóñez, Gerente de Información, Tecnología y Proyectos de UNACEM Ecuador, sobre los desafíos, mitos y realidades de implementar IA en la matriz productiva e industrial del país.',
        'video',
        'https://www.youtube.com/embed/DIjc05gWxbo',
        'Punto de Encuentro',
        'GENERAL',
        true,
        NOW() - INTERVAL '2 days',
        NULL
    ),
    (
        'La inteligencia artificial es una realidad en las empresas de Ecuador',
        'Según un estudio de IBM sobre adopción de IA en América Latina, empresas grandes y medianas en Ecuador han experimentado un aumento del 15% en ingresos al emplear IA en ventas, atención de reclamos y centros de llamadas.',
        'video',
        'https://www.youtube.com/embed/w95fT51mhvY',
        'Televistazo en la Comunidad',
        'GENERAL',
        false,
        NOW() - INTERVAL '8 days',
        NULL
    ),
    (
        'Programa de 10.000 becas de Inteligencia Artificial',
        'Iniciativa del Gobierno del Ecuador, en coordinación con Senescyt, Mintel y Mineduc, para formar a jóvenes ecuatorianos en herramientas de IA y prompt engineering, con cooperación de Emiratos Árabes Unidos.',
        'link',
        'https://www.telecomunicaciones.gob.ec/ecuador-lanza-10-mil-becas-de-inteligencia-artificial-gracias-a-la-cooperacion-con-emiratos-arabes-unidos/',
        'SENESCYT / MINTEL',
        'GENERAL',
        false,
        NOW() - INTERVAL '14 days',
        NULL
    ),
    (
        'Cursos de IA avalados por Senescyt: FLACSO, EPN y UDLA',
        'Recopilación de cursos y diplomados en inteligencia artificial ofrecidos por universidades ecuatorianas, incluyendo formación en modelos como GPT-4 y automatización.',
        'link',
        'https://www.expreso.ec/ciencia-y-tecnologia/senescyt-ofrece-curso-gratuito-inteligencia-artificial-acceder-244939.html',
        'Diario Expreso',
        'GENERAL',
        false,
        NOW() - INTERVAL '20 days',
        NULL
    ),
    (
        'Educación superior en Ecuador se adapta a la inteligencia artificial',
        'Reportaje sobre cómo universidades ecuatorianas están implementando tutores virtuales inteligentes y plataformas adaptativas de aprendizaje.',
        'link',
        'https://www.expreso.ec/buenavida/educacion-superior-ecuador-adapta-inteligencia-artificial-247119.html',
        'Diario Expreso',
        'GENERAL',
        false,
        NOW() - INTERVAL '26 days',
        NULL
    ),
    (
        'La IA en la evaluación formativa en la educación superior ecuatoriana',
        'Análisis de las oportunidades y desafíos de la inteligencia artificial aplicada a la evaluación formativa en universidades del país.',
        'link',
        'https://polodelconocimiento.com/ojs/index.php/es/article/view/11866',
        'Polo del Conocimiento',
        'GENERAL',
        false,
        NOW() - INTERVAL '32 days',
        NULL
    );