package org.atzberger.mango.atz3d;

/**
 *
 * Linear algebra routines needed for many of the operations associated with the
 * 3D rendering engine.
 *
 * @author Paul J. Atzberger
 *
 * @see <a href="http://www.atzberger.org">http://www.atzberger.org</a>
 */
public class Atz_LinearAlgebra {

  /**
   * Transpose a given matrix represented using our array format.
   *
   * @param m
   * @param n
   * @param matrix
   * @param result
   */
  static public void matrixTranspose(int m, int n, double matrix[], double result[]) {

    int i, j;

    for (i = 0; i < m; i++) {
      for (j = 0; j < n; j++) {
        result[j * m + i] = matrix[i * n + j];
      }
    }

  }

  /**
   *
   * Multiply a collection of points (vectors) on the left by a given matrix.
   *
   * @param m
   * @param n
   * @param matrix
   * @param ptsX
   * @param result
   */
  static public void matrixLeftMultPtsX(int m, int n, double matrix[], double ptsX[], double result[]) {
    int i, j, k;

    for (k = 0; k < ptsX.length / n; k++) {
      for (i = 0; i < m; i++) {
        result[k * n + i] = 0;
        for (j = 0; j < n; j++) {
          result[k * n + i] += matrix[i * n + j] * ptsX[k * n + j];
        }
      }
    } /* end of k loop */

  }

  /**
   * Multiply a vector by a matrix on the left.
   *
   * @param m
   * @param n
   * @param matrix
   * @param vec
   * @param result
   */
  static public void matrixVecMult(int m, int n, double matrix[], double vec[], double result[]) {
    int i, j, k;

    for (i = 0; i < m; i++) {
      result[i] = 0;
      for (j = 0; j < n; j++) {
        result[i] += matrix[i * n + j] * vec[j];
      }
    }

  }

  /**
   *
   * Multiply two matrices.
   *
   * @param m1
   * @param n1
   * @param matrixA
   * @param m2
   * @param n2
   * @param matrixB
   * @param result
   */
  static public void matrixMatrixMult(int m1, int n1, double matrixA[],
    int m2, int n2, double matrixB[],
    double result[]) {

    int i, j, k;

    for (i = 0; i < n1; i++) {
      for (j = 0; j < m1; j++) {

        result[i * n1 + j] = 0;
        for (k = 0; k < m2; k++) {
          result[i * n1 + j] += matrixA[i * n1 + k] * matrixB[k * n2 + j];
        }

      }
    }

  }

  /**
   *
   * Compute the norm of a vector
   *
   * @param vec
   * @return vector norm
   */
  static public double vecNorm(double vec[]) {

    int d;
    int N = vec.length;
    double norm;

    norm = 0.0;
    for (d = 0; d < N; d++) {
      norm += vec[d] * vec[d];
    }
    norm = java.lang.Math.sqrt(norm);

    return norm;
  }



  /**
   *
   * Performs rotation of a vector w about the axis vec_theta by the amount theta.
   * <p>
   * This is done by using a rotation matrix about the z-axis and performing a change
   * of variable to the appropriate axis q = vec_theta/norm(vec_theta).
   * <p>
   * This can be done by observing that z can be mapped to any point p = -q
   * by using reflection about the plane orthogonal to v = z - p  which
   * passes through the origin.  The reflection corresponds to the Householder transformation
   * H = I - 2*v*v^T, p = Hz. To obtain a rotation we need to make this unitary matrix
   * orientation preserving (determinant positive), which can be done by reflecting
   * through the origin to obtain G = -H.  This gives Gz = q, where G is now a rotation
   * matrix.  Note that v = (z - p)/norm(z - p) = (z + q)/norm(z + q).  In the case that
   * (z - p) is nearly zero we have that G is approximately the identify I.
   * <p>
   * The rotation matrix is then given by M = G*R*G^T.
   * <p>
   * To compute the rotation we avoid construction of the matrix and instead compute the action
   * of the above operators, which is more efficient.
   * <p>
   *
   * @param vec_theta
   * @param vec_w
   * @param vec_result
   */
  static public void performRotation(double vec_theta[], double vec_w[], double vec_result[]) {

    /*
    const char *error_str_code = "codes.c";
    const char *error_str_func = "performRotation()";
     *
     */

    double theta;
    double norm_v;

    double dot_v_w;
    double dot_v_b;

    double vec_v[] = new double[3];
    double vec_q[] = new double[3];
    double vec_z[] = new double[3];

    double vec_a[] = new double[3];
    double vec_b[] = new double[3];

    /* -- determine the angle of rotation */
    theta = Atz_LinearAlgebra.vecNorm(vec_theta);

    if (theta < 1e-5) { /* if theta is small then we approximate the rotation by
                         * simply the identity to avoid round-off errors. */

      vec_result[0] = vec_w[0];
      vec_result[1] = vec_w[1];
      vec_result[2] = vec_w[2];

    } else { /* compute the action of the rotation matrix, result = Mw = G*R*G^Tw. */

      /* -- determine the rotation axis, vec_q */
      vec_q[0] = vec_theta[0] / theta;
      vec_q[1] = vec_theta[1] / theta;
      vec_q[2] = vec_theta[2] / theta;

      /* -- set z-axis (tranform to make this canonical axis of rotation) */
      vec_z[0] = 0;
      vec_z[1] = 0;
      vec_z[2] = 1.0;

      /* -- compute a = G^T w = (-I + 2*vv^T)w */
      vec_v[0] = vec_z[0] + vec_q[0];
      vec_v[1] = vec_z[1] + vec_q[1];
      vec_v[2] = vec_z[2] + vec_q[2];

      norm_v = Atz_LinearAlgebra.vecNorm(vec_v);

      if (norm_v < 1e-5) { /* if the norm is small the rotation is nearly about
                              * the -z-axis, so no need to change the basis. */
        vec_a[0] = vec_w[0];
        vec_a[1] = vec_w[1];
        vec_a[2] = vec_w[2];

        /* reverse the sign of theta, since positive z-axis assumed */
        theta = -theta;

      } else { /* change the basis in which rotation acts
                * using the above discussed reflection approach */

        vec_v[0] = vec_v[0] / norm_v;
        vec_v[1] = vec_v[1] / norm_v;
        vec_v[2] = vec_v[2] / norm_v;

        dot_v_w = vec_v[0] * vec_w[0]
                + vec_v[1] * vec_w[1]
                + vec_v[2] * vec_w[2];

        vec_a[0] = -vec_w[0] + 2 * vec_v[0] * dot_v_w;
        vec_a[1] = -vec_w[1] + 2 * vec_v[1] * dot_v_w;
        vec_a[2] = -vec_w[2] + 2 * vec_v[2] * dot_v_w;

      } /* end of else */

      /* -- compute b = R*a */
      vec_b[0] = vec_a[0] * java.lang.Math.cos(theta) - vec_a[1] * java.lang.Math.sin(theta);
      vec_b[1] = vec_a[0] * java.lang.Math.sin(theta) + vec_a[1] * java.lang.Math.cos(theta);
      vec_b[2] = vec_a[2];

      /* -- compute the result, result = G*b */
      if (norm_v < 1e-5) { /* if norm of v is small the rotation is nearly
                            * about the -z-axis so v^Tz is nearly zero and there
                            * is no significant change of basis. */

        vec_result[0] = vec_b[0];
        vec_result[1] = vec_b[1];
        vec_result[2] = vec_b[2];

      } else { /* compute the change of basis back to the original coordinate system */

        dot_v_b = vec_v[0] * vec_b[0]
                + vec_v[1] * vec_b[1]
                + vec_v[2] * vec_b[2];

        vec_result[0] = -vec_b[0] + 2 * vec_v[0] * dot_v_b;
        vec_result[1] = -vec_b[1] + 2 * vec_v[1] * dot_v_b;
        vec_result[2] = -vec_b[2] + 2 * vec_v[2] * dot_v_b;

      } /* end else */

    } /* end of theta small */

  }

  /**
   *
   * Create rotation matrix about a given axis.  The norm of the axis vector
   * is used to determine the angular displacement for the rotation.
   *
   * @param vec_theta
   * @param matrix_result
   */
  static public void genRotationMatrix(double vec_theta[], double matrix_result[]) {

    double tmp_vec1[] = new double[3];
    double tmp_vec2[] = new double[3];

    int num_dim = 3;

    /* generate the matrix by looking at results for x-axis, y-axis, z-axis */

    /* first column */
    tmp_vec1[0] = 1.0;
    tmp_vec1[1] = 0;
    tmp_vec1[2] = 0;
    performRotation(vec_theta, tmp_vec1, tmp_vec2);
    matrix_result[0 * num_dim + 0] = tmp_vec2[0];
    matrix_result[1 * num_dim + 0] = tmp_vec2[1];
    matrix_result[2 * num_dim + 0] = tmp_vec2[2];

    /* second column */
    tmp_vec1[0] = 0;
    tmp_vec1[1] = 1.0;
    tmp_vec1[2] = 0;
    performRotation(vec_theta, tmp_vec1, tmp_vec2);
    matrix_result[0 * num_dim + 1] = tmp_vec2[0];
    matrix_result[1 * num_dim + 1] = tmp_vec2[1];
    matrix_result[2 * num_dim + 1] = tmp_vec2[2];

    /* third column */
    tmp_vec1[0] = 0;
    tmp_vec1[1] = 0;
    tmp_vec1[2] = 1.0;
    performRotation(vec_theta, tmp_vec1, tmp_vec2);
    matrix_result[0 * num_dim + 2] = tmp_vec2[0];
    matrix_result[1 * num_dim + 2] = tmp_vec2[1];
    matrix_result[2 * num_dim + 2] = tmp_vec2[2];

  }

} /* end Atz_LinearAlgebra */

