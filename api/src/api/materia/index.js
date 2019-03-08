import { Router } from 'express'
import { middleware as query } from 'querymen'
import { middleware as body } from 'bodymen'
import { token } from '../../services/passport'
import { create, index, show, update, destroy } from './controller'
import { schema } from './model'
export Materia, { schema } from './model'

const router = new Router()
const { nombre } = schema.tree

/**
 * @api {post} /materias Create materia
 * @apiName CreateMateria
 * @apiGroup Materia
 * @apiPermission admin
 * @apiParam {String} access_token admin access token.
 * @apiParam nombre Materia's nombre.
 * @apiSuccess {Object} materia Materia's data.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 * @apiError 404 Materia not found.
 * @apiError 401 admin access only.
 */
router.post('/',
  token({ required: true, roles: ['admin'] }),
  body({ nombre }),
  create)

/**
 * @api {get} /materias Retrieve materias
 * @apiName RetrieveMaterias
 * @apiGroup Materia
 * @apiPermission user
 * @apiParam {String} access_token user access token.
 * @apiUse listParams
 * @apiSuccess {Number} count Total amount of materias.
 * @apiSuccess {Object[]} rows List of materias.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 * @apiError 401 user access only.
 */
router.get('/',
  token({ required: true }),
  query(),
  index)

/**
 * @api {get} /materias/:id Retrieve materia
 * @apiName RetrieveMateria
 * @apiGroup Materia
 * @apiPermission user
 * @apiParam {String} access_token user access token.
 * @apiSuccess {Object} materia Materia's data.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 * @apiError 404 Materia not found.
 * @apiError 401 user access only.
 */
router.get('/:id',
  token({ required: true }),
  show)

/**
 * @api {put} /materias/:id Update materia
 * @apiName UpdateMateria
 * @apiGroup Materia
 * @apiPermission admin
 * @apiParam {String} access_token admin access token.
 * @apiParam nombre Materia's nombre.
 * @apiSuccess {Object} materia Materia's data.
 * @apiError {Object} 400 Some parameters may contain invalid values.
 * @apiError 404 Materia not found.
 * @apiError 401 admin access only.
 */
router.put('/:id',
  token({ required: true, roles: ['admin'] }),
  body({ nombre }),
  update)

/**
 * @api {delete} /materias/:id Delete materia
 * @apiName DeleteMateria
 * @apiGroup Materia
 * @apiPermission admin
 * @apiParam {String} access_token admin access token.
 * @apiSuccess (Success 204) 204 No Content.
 * @apiError 404 Materia not found.
 * @apiError 401 admin access only.
 */
router.delete('/:id',
  token({ required: true, roles: ['admin'] }),
  destroy)

export default router
