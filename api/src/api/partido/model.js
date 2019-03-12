import mongoose, { Schema } from 'mongoose'

const partidoSchema = new Schema({
  nombre: {
    type: String
  },
  siglas: {
    type: String
  },
  picture: {
    type: String,
    trim: true
  },
  propuestas: [{
    type: Schema.Types.ObjectId,
    ref: 'Propuesta',
    default: null
  }]
}, {
  timestamps: true,
  toJSON: {
    virtuals: true,
    transform: (obj, ret) => { delete ret._id }
  }
})

partidoSchema.methods = {
  view (full) {
    const view = {
      // simple view
      id: this.id,
      nombre: this.nombre,
      siglas: this.siglas,
      picture: this.picture,
      propuestas: this.propuestas,
      createdAt: this.createdAt,
      updatedAt: this.updatedAt
    }

    return full ? {
      ...view
      // add properties for a full view
    } : view
  }
}

const model = mongoose.model('Partido', partidoSchema)

export const schema = model.schema
export default model
