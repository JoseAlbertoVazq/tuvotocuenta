import { Router } from 'express'
import { middleware as query } from 'querymen'
import { middleware as body } from 'bodymen'
import { token } from '../../services/passport'
import { create, index, show, update, destroy } from './controller'
import { schema } from './model'
export Partido, { schema } from './model'

const router = new Router()
const { nombre, siglas, picture, propuestas } = schema.tree

/**
 * @api {post} /partidos Create partido
 * @apiName CreatePartido
 * @apiGroup Partido
 * @apiPermission admin
 * @apiParam {String} access_token admin access token.
 * @apiParam nombre Partido's nombre.
 * @apiParam siglas Partido's siglas.
 * @apiParam propuestas Partido's propuestas.
 * @apiSuccess {Object} partido Partido's data.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 * @apiError 404 Partido not found.
 * @apiError 401 admin access only.
 */
router.post('/',
  token({ required: true, roles: ['admin'] }),
  body({ nombre, siglas, picture, propuestas }),
  create)

/**
 * @api {get} /partidos Retrieve partidos
 * @apiName RetrievePartidos
 * @apiGroup Partido
 * @apiPermission user
 * @apiParam {String} access_token user access token.
 * @apiUse listParams
 * @apiSuccess {Number} count Total amount of partidos.
 * @apiSuccess {Object[]} rows List of partidos.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 * @apiError 401 user access only.
 */
router.get('/',
  token({ required: true }),
  query(),
  index)

/**
 * @api {get} /partidos/:id Retrieve partido
 * @apiName RetrievePartido
 * @apiGroup Partido
 * @apiPermission user
 * @apiParam {String} access_token user access token.
 * @apiSuccess {Object} partido Partido's data.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 * @apiError 404 Partido not found.
 * @apiError 401 user access only.
 */
router.get('/:id',
  token({ required: true }),
  show)

/**
 * @api {put} /partidos/:id Update partido
 * @apiName UpdatePartido
 * @apiGroup Partido
 * @apiPermission admin
 * @apiParam {String} access_token admin access token.
 * @apiParam nombre Partido's nombre.
 * @apiParam siglas Partido's siglas.
 * @apiParam propuestas Partido's propuestas.
 * @apiSuccess {Object} partido Partido's data.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 * @apiError 404 Partido not found.
 * @apiError 401 admin access only.
 */
router.put('/:id',
  token({ required: true, roles: ['admin'] }),
  body({ nombre, siglas, picture, propuestas }),
  update)

/**
 * @api {delete} /partidos/:id Delete partido
 * @apiName DeletePartido
 * @apiGroup Partido
 * @apiPermission admin
 * @apiParam {String} access_token admin access token.
 * @apiSuccess (Success 204) 204 No Content.
 * @apiError 404 Partido not found.
 * @apiError 401 admin access only.
 */
router.delete('/:id',
  token({ required: true, roles: ['admin'] }),
  destroy)

export default router
