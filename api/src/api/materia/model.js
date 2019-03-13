import mongoose, { Schema } from 'mongoose'

const materiaSchema = new Schema({
  nombre: {
    type: String,
    unique: true,
    trim: true,
    lowercase: true
  }
}, {
  timestamps: true,
  toJSON: {
    virtuals: true,
    transform: (obj, ret) => { delete ret._id }
  }
})

materiaSchema.methods = {
  view (full) {
    const view = {
      // simple view
      id: this.id,
      nombre: this.nombre,
      createdAt: this.createdAt,
      updatedAt: this.updatedAt
    }

    return full ? {
      ...view
      // add properties for a full view
    } : view
  }
}

const model = mongoose.model('Materia', materiaSchema)

export const schema = model.schema
export default model
