-- Migración idempotente:
-- 1) Elimina 2 recursos de ETHICS cuyas URLs ya no existen.
-- 2) Inserta 2 nuevos recursos (link) sobre principios éticos de IA en Ecuador,
--    solo si no existen ya (evita duplicados si la migración se corre más de una vez).

-- ============================================================
-- DELETE: recursos con enlaces caídos
-- ============================================================

DELETE FROM resources
WHERE url IN (
              'https://ctslab.org/etica-ia-america-latina',
              'https://link.springer.com/article/10.1007/s40979-025-00209-3'
    );

-- ============================================================
-- INSERT: nuevos recursos ETHICS (idempotente vía NOT EXISTS)
-- ============================================================

INSERT INTO resources (title, description, type, url, source, topic, featured, created_at, updated_at)
SELECT
    'Ecuador adopta primer Código de Ética de Inteligencia Artificial',
    'La Superintendencia de Competencia Económica adopta el primer marco normativo de carácter ético emitido por una institución pública ecuatoriana para regular el uso de la IA, bajo el lema "Utiliza la IA con criterio", con la participación de UNESCO y la red #Women4EthicalAI.',
    'link',
    'https://www.unesco.org/es/articles/ecuador-adopta-primer-codigo-de-etica-de-inteligencia-artificial-ia-en-institucion-publica',
    'UNESCO',
    'ETHICS',
    false,
    NOW() - INTERVAL '10 days',
    NULL
WHERE NOT EXISTS (
    SELECT 1 FROM resources
    WHERE url = 'https://www.unesco.org/es/articles/ecuador-adopta-primer-codigo-de-etica-de-inteligencia-artificial-ia-en-institucion-publica'
    );

INSERT INTO resources (title, description, type, url, source, topic, featured, created_at, updated_at)
SELECT
    'UNESCO llama desde Ecuador a una gobernanza ética de la inteligencia artificial',
    'Durante la conferencia "Ética de la Inteligencia Artificial", UNESCO expone los principios de una "regulación virtuosa" de la IA en Ecuador: justicia social, participación ciudadana, respeto a la dignidad humana y cooperación internacional, en línea con la Recomendación sobre la Ética de la IA (2021).',
    'link',
    'https://www.unesco.org/es/articles/unesco-llama-desde-ecuador-una-gobernanza-etica-de-la-inteligencia-artificial',
    'UNESCO',
    'ETHICS',
    false,
    NOW() - INTERVAL '9 days',
    NULL
WHERE NOT EXISTS (
    SELECT 1 FROM resources
    WHERE url = 'https://www.unesco.org/es/articles/unesco-llama-desde-ecuador-una-gobernanza-etica-de-la-inteligencia-artificial'
    );