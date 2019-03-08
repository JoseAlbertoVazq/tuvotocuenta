import { Router } from 'express'
import { middleware as query } from 'querymen'
import { middleware as body } from 'bodymen'
import { token } from '../../services/passport'
import { create, index, show, update, destroy } from './controller'
import { schema } from './model'
export Propuesta, { schema } from './model'

const router = new Router()
const { titulo, contenido, materia, partido } = schema.tree

/**
 * @api {post} /propuestas Create propuesta
 * @apiName CreatePropuesta
 * @apiGroup Propuesta
 * @apiPermission user
 * @apiParam {String} access_token user access token.
 * @apiParam titulo Propuesta's titulo.
 * @apiParam contenido Propuesta's contenido.
 * @apiParam materia Propuesta's materia.
 * @apiParam partido Propuesta's partido.
 * @apiSuccess {Object} propuesta Propuesta's data.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 * @apiError 404 Propuesta not found.
 * @apiError 401 user access only.
 */
router.post('/',
  token({ required: true }),
  body({ titulo, contenido, materia, partido }),
  create)

/**
 * @api {get} /propuestas Retrieve propuestas
 * @apiName RetrievePropuestas
 * @apiGroup Propuesta
 * @apiPermission user
 * @apiParam {String} access_token user access token.
 * @apiUse listParams
 * @apiSuccess {Number} count Total amount of propuestas.
 * @apiSuccess {Object[]} rows List of propuestas.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 * @apiError 401 user access only.
 */
router.get('/',
  token({ required: true }),
  query(),
  index)

/**
 * @api {get} /propuestas/:id Retrieve propuesta
 * @apiName RetrievePropuesta
 * @apiGroup Propuesta
 * @apiPermission user
 * @apiParam {String} access_token user access token.
 * @apiSuccess {Object} propuesta Propuesta's data.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 * @apiError 404 Propuesta not found.
 * @apiError 401 user access only.
 */
router.get('/:id',
  token({ required: true }),
  show)

/**
 * @api {put} /propuestas/:id Update propuesta
 * @apiName UpdatePropuesta
 * @apiGroup Propuesta
 * @apiPermission user
 * @apiParam {String} access_token user access token.
 * @apiParam titulo Propuesta's titulo.
 * @apiParam contenido Propuesta's contenido.
 * @apiParam materia Propuesta's materia.
 * @apiParam partido Propuesta's partido.
 * @apiSuccess {Object} propuesta Propuesta's data.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 * @apiError 404 Propuesta not found.
 * @apiError 401 user access only.
 */
router.put('/:id',
  token({ required: true }),
  body({ titulo, contenido, materia, partido }),
  update)

/**
 * @api {delete} /propuestas/:id Delete propuesta
 * @apiName DeletePropuesta
 * @apiGroup Propuesta
 * @apiPermission user
 * @apiParam {String} access_token user access token.
 * @apiSuccess (Success 204) 204 No Content.
 * @apiError 404 Propuesta not found.
 * @apiError 401 user access only.
 */
router.delete('/:id',
  token({ required: true }),
  destroy)

export default router
