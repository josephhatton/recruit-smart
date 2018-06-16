(function () {
  'use strict';

  angular
    .module('recruitsmartApp')
    .config(stateConfig);

  stateConfig.$inject = ['$stateProvider'];

  function stateConfig($stateProvider) {
    $stateProvider
      .state('job-order-internal-comment', {
        parent: 'entity',
        url: '/job-order-internal-comment',
        data: {
          authorities: ['ROLE_USER'],
          pageTitle: 'JobOrderInternalComments'
        },
        views: {
          'content@': {
            templateUrl: 'app/entities/job-order-internal-comment/job-order-internal-comments.html',
            controller: 'JobOrderInternalCommentController',
            controllerAs: 'vm'
          }
        },
        resolve: {}
      })
      .state('job-order-internal-comment-detail', {
        parent: 'job-order-internal-comment',
        url: '/job-order-internal-comment/{id}',
        data: {
          authorities: ['ROLE_USER'],
          pageTitle: 'JobOrderInternalComment'
        },
        views: {
          'content@': {
            templateUrl: 'app/entities/job-order-internal-comment/job-order-internal-comment-detail.html',
            controller: 'JobOrderInternalCommentDetailController',
            controllerAs: 'vm'
          }
        },
        resolve: {
          entity: ['$stateParams', 'JobOrderInternalComment', function ($stateParams, JobOrderInternalComment) {
            return JobOrderInternalComment.get({id: $stateParams.id}).$promise;
          }],
          previousState: ["$state", function ($state) {
            var currentStateData = {
              name: $state.current.name || 'job-order-internal-comment',
              params: $state.params,
              url: $state.href($state.current.name, $state.params)
            };
            return currentStateData;
          }]
        }
      })
      .state('job-order-internal-comment-detail.edit', {
        parent: 'job-order-internal-comment-detail',
        url: '/detail/edit',
        data: {
          authorities: ['ROLE_USER']
        },
        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
          $uibModal.open({
            templateUrl: 'app/entities/job-order-internal-comment/job-order-internal-comment-dialog.html',
            controller: 'JobOrderInternalCommentDialogController',
            controllerAs: 'vm',
            backdrop: 'static',
            size: 'lg',
            resolve: {
              entity: ['JobOrderInternalComment', function (JobOrderInternalComment) {
                return JobOrderInternalComment.get({id: $stateParams.id}).$promise;
              }]
            }
          }).result.then(function () {
            $state.go('^', {}, {reload: false});
          }, function () {
            $state.go('^');
          });
        }]
      })
      .state('job-order-internal-comment.new', {
        parent: 'job-order-internal-comment',
        url: '/new',
        data: {
          authorities: ['ROLE_USER']
        },
        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
          $uibModal.open({
            templateUrl: 'app/entities/job-order-internal-comment/job-order-internal-comment-dialog.html',
            controller: 'JobOrderInternalCommentDialogController',
            controllerAs: 'vm',
            backdrop: 'static',
            size: 'lg',
            resolve: {
              entity: function () {
                return {
                  name: null,
                  description: null,
                  id: null
                };
              }
            }
          }).result.then(function () {
            $state.go('job-order-internal-comment', null, {reload: 'job-order-internal-comment'});
          }, function () {
            $state.go('job-order-internal-comment');
          });
        }]
      })
      .state('job-order-internal-comment.edit', {
        parent: 'job-order-internal-comment',
        url: '/{id}/edit',
        data: {
          authorities: ['ROLE_USER']
        },
        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
          $uibModal.open({
            templateUrl: 'app/entities/job-order-internal-comment/job-order-internal-comment-dialog.html',
            controller: 'JobOrderInternalCommentDialogController',
            controllerAs: 'vm',
            backdrop: 'static',
            size: 'lg',
            resolve: {
              entity: ['JobOrderInternalComment', function (JobOrderInternalComment) {
                return JobOrderInternalComment.get({id: $stateParams.id}).$promise;
              }]
            }
          }).result.then(function () {
            $state.go('job-order-internal-comment', null, {reload: 'job-order-internal-comment'});
          }, function () {
            $state.go('^');
          });
        }]
      })
      .state('job-order-internal-comment.delete', {
        parent: 'job-order-internal-comment',
        url: '/{id}/delete',
        data: {
          authorities: ['ROLE_USER']
        },
        onEnter: ['$stateParams', '$state', '$uibModal', function ($stateParams, $state, $uibModal) {
          $uibModal.open({
            templateUrl: 'app/entities/job-order-internal-comment/job-order-internal-comment-delete-dialog.html',
            controller: 'JobOrderInternalCommentDeleteController',
            controllerAs: 'vm',
            size: 'md',
            resolve: {
              entity: ['JobOrderInternalComment', function (JobOrderInternalComment) {
                return JobOrderInternalComment.get({id: $stateParams.id}).$promise;
              }]
            }
          }).result.then(function () {
            $state.go('job-order-internal-comment', null, {reload: 'job-order-internal-comment'});
          }, function () {
            $state.go('^');
          });
        }]
      });
  }

})();
